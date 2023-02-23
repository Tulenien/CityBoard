import { combineReducers } from "redux";
import {userReducer} from "./userReducer";
import {advertsReducer} from "./advertReducer";

export const rootReducer = combineReducers({
    user: userReducer,
    advert: advertsReducer,
});