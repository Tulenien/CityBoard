import React, {FC, useEffect} from 'react'
import {useSelector} from "react-redux";
import {TStore} from "../../types/types";
import {TAdvertState} from "../../redux/reducers/advertReducer";
import {Adverts, PagedAdverts} from "../../models";
import {getAdvert, getAdverts} from "../../api/averts-api";
import {boundAdverts} from "../../redux/actions/advertActions";
import Table from "../../components/Table";
import {advertS} from "../../models/data";
import {Card} from "../../components/Card";

//const Advert=()=> {
//    return (
//        <div>
//            Advert page
//        </div>
//    )
//}
interface AdvertProps{}
const Advert: FC<AdvertProps> = ({}) => {
    const {advert} = useSelector<TStore, TAdvertState>(
        (store) => store.advert
    );
    useEffect(() => {
        getAdvert(1)
            .then((data: Adverts) => {
                boundAdverts.setAdvert(data);
                console.log(data);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    return (
        <div>
            <Card advert={advert}/>
        </div>
    )
}


export default Advert