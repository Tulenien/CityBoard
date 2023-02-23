import {Users, PagedUsers, Roles, UserStatus} from "../../models";
import { TUserAction } from "../actions/userActions";

export type TUserState = {
    user: Users;
    users: PagedUsers;
};

const initialState: TUserState = {
    user: {
        id: 0,
        username: "",
        password: "",
        name: "",
        surname: "",
        middle_name: "",
        status: UserStatus.ACTIVE,
        password_expired: false,
        roles: [Roles.ROLE_USER],
    },
    users: {
        page: {
            empty: true,
        },
        paging: {
            nextEnabled: false,
            prevEnabled: false,
        }
    }
};

export const userReducer = (state = initialState, action: TUserAction) => {
    switch (action.type) {
        case "SET_USER":
            return {
                ...state,
                user: { ...action.payload },
            };
        case "SET_USERS":
            return {
                ...state,
                users: { ...action.payload },
            };
        case "LOGOUT":
            return {
                ...state,
                user: {
                    id: 0,
                    username: "",
                    password: "",
                    status: UserStatus.ACTIVE,
                    password_expired: false,
                },
            };
        default:
            return state;
    }
};