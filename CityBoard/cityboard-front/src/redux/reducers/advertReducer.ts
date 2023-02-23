import { Adverts, PagedAdverts } from "../../models";
import { TAdvertAction } from "../actions/advertActions";

export type TAdvertState = {
    advert: Adverts;
    adverts: PagedAdverts;
};

const initialState: TAdvertState = {
    advert: {
        id: 1,
        type: "RENT",
        email: "",
        phone: "",
        status: "VISIBLE",
        city: "Иваново",
        district: "Port",
        street: "пл. Первомайская, 714",
        houseCode: "922",
        flatNumber: 0,
        floor: 49,
        floors: 76,
        roomsNumber: 0,
        area: 0,
        livingArea: 0,
        price: 243000,
        description: "Приятное жилище для вас! Всего за 243000 в месяц",
        modCheck: false,
        authorId: 0,
    },
    adverts: {
        page: {
            empty: true,
        },
        paging: {
            nextEnabled: false,
            prevEnabled: false,
        }
    }
};

export const advertsReducer = (state = initialState, action: TAdvertAction) => {
    switch (action.type) {
        case "SET_ADVERT":
            return {
                ...state,
                pets: {...action.payload},
            };
        case "SET_ADVERTS":
            return {
                ...state,
                shops: {...action.payload},
            };

        default:
            return state;
    }
};