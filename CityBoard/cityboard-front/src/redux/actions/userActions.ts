import {store} from "../store";
import {bindActionCreators} from "redux";
import {SET_USERS, SET_USER, SET_AUTHED, LOGOUT} from "../action-types/usersActionsTypes";
import { Users, PagedUsers } from "../../models";


export interface ISetUser {
    readonly type: typeof SET_USER;
    readonly payload: Users;
}

export interface ISetUsers {
    readonly type: typeof SET_USERS;
    readonly payload: PagedUsers;
}

export interface ILogout {
    readonly type: typeof LOGOUT;
}

export type TUserAction = ISetUser | ISetUsers | ILogout;

const doSetUser = (user: Users): ISetUser => ({
    type: SET_USER,
    payload: user,
});
const doSetUsers = (users: PagedUsers): ISetUsers => ({
    type: SET_USERS,
    payload: users,
});
const doLogout = (): ILogout => ({
    type: LOGOUT,
});

export const boundUser = bindActionCreators(
    {
        setUser: doSetUser,
        setUsers: doSetUsers,
        logout: doLogout,
    },
    store.dispatch
);