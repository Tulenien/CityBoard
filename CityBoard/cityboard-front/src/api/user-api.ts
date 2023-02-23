import {boundUser} from "../redux/actions/userActions";
import {API_URL} from "./url-config";
import {TAnswer} from "../types/types";
import {JwtRequest, Users} from "../models";

export const getUsers = () => {
    return fetch(`${API_URL}/users`)
        .then((res) => {
            return res.json()
        })
        .then((answer: TAnswer) => {
            if (answer.success) {
                boundUser.setUsers(answer.payload);
            }
            else {
                return Promise.reject(answer.payload);
            }
        });
}

export const register = (user: Users) => {
    return fetch(`${API_URL}/registration`, {
        method: "post",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    })
        .then((res) => {
            return res.json();
        })
        .then((answer: TAnswer) => {
            if (answer.success) {
                boundUser.setUser(answer.payload);
            }
            //alert(answer.message);
        });
};

export const login = (authData: JwtRequest) => {
    return fetch(`${API_URL}/login`, {
        method: "post",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(authData),
    })
        .then((res) => {
            return res.json();
        })
        .then((answer: TAnswer) => {
            if (answer.success) {
                console.log(answer.payload);
                localStorage.setItem("token", answer.payload.token);
                boundUser.setUser(answer.payload);
            }
            alert(answer.message);
        });
};

export const refreshToken = () => {
    const token = localStorage.getItem("token");
    return fetch(`${API_URL}/token`, {
        method: "get",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
    })
        .then((res) => {
            return res.json();
        })
        .then((answer: TAnswer) => {
            if (answer.success) {
                console.log(answer.payload);
                boundUser.setUser(answer.payload);
            }
            if (answer.message === "Token Expired") {
                localStorage.removeItem("token");
            }
            alert(answer.message);
        });
};