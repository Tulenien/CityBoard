import React, {FC, useEffect} from 'react'
import {getAdverts} from "../../api/averts-api";
import {advertS} from "../../models/data";
import {Adverts, PagedAdverts} from "../../models";
import {boundAdverts, TAdvertAction} from "../../redux/actions/advertActions";
import {useSelector} from "react-redux";
import {TAdvertState} from "../../redux/reducers/advertReducer";
import {TStore} from "../../types/types";
import Table from "../../components/Table";

interface HomeProps{}
const Home: FC<HomeProps> = ({}) => {
    const {adverts} = useSelector<TStore, TAdvertState>(
        (store) => store.advert
    );
    let tabledAdverts: Adverts[] = [];
    useEffect(() => {
        getAdverts()
            .then((data: PagedAdverts) => {
                boundAdverts.setAdverts(data);
                if (data.page?.content!) {
                    tabledAdverts = data.page?.content;
                }
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    return (
        <div>
            <Table
                   headers={{
                       id: "#",
                       type: "Тип",
                       city: "Город",
                       district: "Район",
                       street: "Улица",
                       houseCode: "Дом",
                       flatNumber: "Квартира",
                       floor: "Этаж",
                       floors: "Этажи",
                       roomsNumber: "Комнат",
                       area: "Площадь",
                       livingArea: "Жилая площадь",
                       price: "Цена",
                       description: "Описание"
                }}
                   items={advertS}
                   customRenderers={{
                       id: (it) => (
                           it.id
                       ),
                       type: (it) => (
                           it.type
                       ),
                       city: (it) => (
                           it.city
                       ),
                       district: (it) => (
                           it.district
                       ),
                       street: (it) => (
                           it.street
                       ),
                       houseCode: (it) => (
                           it.houseCode
                       ),
                       flatNumber: (it) => (
                           it.flatNumber
                       ),
                       floor: (it) => (
                           it.floor
                       ),
                       floors: (it) => (
                           it.floors
                       ),
                       roomsNumber: (it) => (
                           it.roomsNumber
                       ),
                       area: (it) => (
                           it.area
                       ),
                       livingArea: (it) => (
                           it.livingArea
                       ),
                       price: (it) => (
                           it.price
                       ),
                       description: (it) => (
                           it.description
                       )
                   }}/>
        </div>
    )
}

export default Home