import {bindActionCreators} from "@reduxjs/toolkit";
import {Adverts, PagedAdverts} from "../../models";
import {SET_ADVERT, SET_ADVERTS} from "../action-types/advertActionTypes"
import {store} from "../store";

//types
export interface ISetAdvert {
    readonly type: typeof SET_ADVERT;
    readonly payload: Adverts;
}
export interface ISetAdverts {
    readonly type: typeof SET_ADVERTS;
    readonly payload: PagedAdverts;
}

export type TAdvertAction = ISetAdvert | ISetAdverts;

//types

const doSetAdvert = (advert: Adverts): ISetAdvert => ({
    type: SET_ADVERT,
    payload: advert,
});
const doSetAdverts = (adverts: PagedAdverts): ISetAdverts => ({
    type: SET_ADVERTS,
    payload: adverts,
});

export const boundAdverts = bindActionCreators(
    {
        setAdvert: doSetAdvert,
        setAdverts: doSetAdverts,
    },
    store.dispatch
);