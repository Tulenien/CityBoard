package com.CityBoard.services;

import com.CityBoard.models.Adverts;
import com.CityBoard.models.Requests;
import com.CityBoard.models.Users;
import com.CityBoard.models.enums.*;
import com.CityBoard.repositories.AdvertsRepository;
import com.CityBoard.repositories.RequestsRepository;
import com.CityBoard.repositories.UsersRepository;
import com.CityBoard.services.dsrouting.DBContextHolder;
import com.CityBoard.services.dsrouting.DBNames;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class Populator {
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final AdvertsRepository advertsRepository;
    @Autowired
    private final RequestsRepository requestsRepository;
    private final Faker fakerRu;
    private final Faker fakerEn;

    public Populator(UsersRepository usersRepository, AdvertsRepository advertsRepository, RequestsRepository requestsRepository) {
        this.usersRepository = usersRepository;
        this.advertsRepository = advertsRepository;
        this.requestsRepository = requestsRepository;
        fakerRu = new Faker(new Locale("ru"));
        fakerEn = new Faker(new Locale("en-US"));
    }

    private int getRandomInteger(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private float getRandomFloatRounded(float min, float max, int places) {
        double value = (Math.random() * (max - min)) + min;
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.FLOOR);
        return bigDecimal.floatValue();
    }

    public Users generateUser(Set<String> usernames, Set<Roles> userRole) {
        String username = fakerEn.name().firstName();
        if (username.contains(username)) {
            Integer duplicateNum = 0;
            while (usernames.contains(username + duplicateNum.toString())) {
                duplicateNum++;
            }
            username = username + duplicateNum.toString();
        }
        usernames.add(username);
        Users user = Users.builder()
                .name(fakerRu.name().firstName())
                .surname(fakerRu.name().lastName())
                .middle_name(fakerRu.name().nameWithMiddle().split(" ")[1])
                .roles(userRole)
                .password_expired(false)
                .password("{noop}12345")
                .username(username)
                .status(UserStatus.ACTIVE)
                .build();
        return user;
    }

    private int generateRoomsNumberFromArea(float area) {
        if (area <= 20) {
            return 1;
        } else if (20 < area && area < 50) {
            return 2;
        } else if (50.1 < area && area < 75) {
            return 3;
        } else if (75.1 < area && area < 100) {
            return 4;
        } else if (100.1 < area && area < 200) {
            return 5;
        }
        return 6;
    }

    public Adverts generateAdvert(AdvertType type) {
        int floors = getRandomInteger(5, 97);
        int floor = getRandomInteger(1, floors);
        float area = getRandomFloatRounded(6, 300, 1);
        BigDecimal bigDecimal = BigDecimal.valueOf(area * 0.9);
        bigDecimal = bigDecimal.setScale(1, RoundingMode.FLOOR);
        float living_area = bigDecimal.floatValue();
        int rooms = generateRoomsNumberFromArea(area);
        Integer price = 1;
        String description = "Приятное жилище для вас! Всего за ";
        if (type == AdvertType.RENT) {
            price *= 1000 * (int) area;
            description += price.toString() + " в месяц";
        } else {
            price *= 50000 * (int) area * rooms;
            description += price.toString() + ".";
        }
        Adverts advert = Adverts.builder()
                .phone(fakerRu.phoneNumber().phoneNumber())
                .email(fakerEn.internet().emailAddress())
                .city(fakerRu.address().cityName())
                .district(fakerRu.address().cityPrefix())
                .street(fakerRu.address().streetAddress())
                .house_code(fakerRu.address().buildingNumber())
                .flat_num(floor * 10 + getRandomInteger(0, 9))
                .floors(floors)
                .floor(floor)
                .area(area)
                .living_area(living_area)
                .rooms_num(rooms)
                .price(price)
                .type(type)
                .status(AdvertStatus.VISIBLE)
                .modCheck(true)
                .description(description)
                .build();
        return advert;
    }

    public List<Users> generateMainUsers(Set<Roles> userRole, Set<String> usernames) {
        List<Users> users = new ArrayList<>();
        userRole.add(Roles.ROLE_ADMIN);
        Users mainUser = Users.builder()
                .name("Тимофей")
                .surname("Евсигнеев")
                .middle_name("Александрович")
                .roles(userRole)
                .password_expired(false)
                .password("{noop}12345")
                .username("Tulenien")
                .status(UserStatus.ACTIVE)
                .build();
        usernames.add("Tulenien");
        users.add(mainUser);
        userRole.remove(Roles.ROLE_ADMIN);

        userRole.add(Roles.ROLE_MOD);
        mainUser = Users.builder()
                .name("Модератор")
                .surname("Модератов")
                .middle_name("Модератор")
                .roles(userRole)
                .password_expired(false)
                .password("{noop}12345")
                .username("Mod")
                .status(UserStatus.ACTIVE)
                .build();
        usernames.add("Mod");
        users.add(mainUser);
        userRole.remove(Roles.ROLE_MOD);

        userRole.add(Roles.ROLE_USER);
        mainUser = Users.builder()
                .name("Пользователь")
                .surname("Пользовательский")
                .middle_name("Пользователев")
                .roles(userRole)
                .password_expired(false)
                .password("{noop}12345")
                .username("User#1")
                .status(UserStatus.ACTIVE)
                .build();
        usernames.add("User#1");
        users.add(mainUser);
        return users;
    }

    public void populateDatabase() {
        // Generate users
        Set<String> usernames = new HashSet<>();
        Set<Roles> userRole = new HashSet<>();
        List<Users> users = generateMainUsers(userRole, usernames);

        // Make 1000 unique users
        System.out.println("\nStarted generation users\n");
        for (int i = 0; i < 100; i++) {
            Users user = generateUser(usernames, userRole);
            users.add(user);
        }
        // Make 2000 unique adverts: 1000 of each type
        List<Adverts> rentAdverts = new ArrayList<>();
        List<Adverts> saleAdverts = new ArrayList<>();
        System.out.println("\nStarted generation sale adverts\n");
        for (int i = 0; i < 100; i++) {
            saleAdverts.add(generateAdvert(AdvertType.SALE));
        }
        System.out.println("\nStarted generation rent adverts\n");
        for (int i = 0; i < 100; i++) {
            rentAdverts.add(generateAdvert(AdvertType.RENT));
        }

        // Make 4000 unique requests: 2000 of each type
        System.out.println("\nStarted assigning adverts and generating requests\n");
        List<Requests> requests = new ArrayList<>();
        // Assign each advert to user (2 types each)
        // Each user makes 2 types of request to next users adverts (4 in total)
        for (int userInd = 3; userInd < users.size(); userInd++) {
            saleAdverts.get(userInd - 3).setUser(users.get(userInd));
            rentAdverts.get(userInd - 3).setUser(users.get(userInd));
            if (userInd < 102) {
                Requests request = Requests.builder()
                        .advert(saleAdverts.get(userInd - 3 + 1))
                        .user(users.get(userInd))
                        .type(RequestType.SALE)
                        .status(RequestStatus.PENDING)
                        .build();
                requests.add(request);
                request.setType(RequestType.VIEWING);
                requests.add(request);
                request.setAdvert(rentAdverts.get(userInd - 3 + 1));
                requests.add(request);
                request.setType(RequestType.RENT);
                requests.add(request);
            }
        }
        // Last user creates request to first adverts
        Requests request = Requests.builder()
                .advert(rentAdverts.get(0))
                .user(users.get(102))
                .type(RequestType.SALE)
                .status(RequestStatus.PENDING)
                .build();
        requests.add(request);
        request.setType(RequestType.VIEWING);
        requests.add(request);
        request.setAdvert(saleAdverts.get(1));
        requests.add(request);
        request.setType(RequestType.RENT);
        requests.add(request);

        usersRepository.saveAll(users);
        advertsRepository.saveAll(rentAdverts);
        advertsRepository.saveAll(saleAdverts);
        requestsRepository.saveAll(requests);
        DBContextHolder.setCurrentConnect(DBNames.DB2);
        usersRepository.saveAll(users);
        advertsRepository.saveAll(rentAdverts);
        advertsRepository.saveAll(saleAdverts);
        requestsRepository.saveAll(requests);
        DBContextHolder.setCurrentConnect(DBNames.DB1);
    }
}
