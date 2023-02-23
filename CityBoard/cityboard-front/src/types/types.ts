import {TUserState} from "../redux/reducers/userReducer";
import {TAdvertState} from "../redux/reducers/advertReducer";


export type TAnswer = {
    success: boolean;
    payload: any;
    message: string;
};

export type TStore = {
    user: TUserState;
    advert: TAdvertState;
};