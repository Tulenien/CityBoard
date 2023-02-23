import {boundAdverts} from "../redux/actions/advertActions";
import {API_URL} from "./url-config";
import {TAnswer} from "../types/types";
import {boundUser} from "../redux/actions/userActions";

export const getAdverts = (page?: number, size?: number) => {
   // const token = localStorage.getItem("token");
    return fetch(`${API_URL}/adverts`, {
        method: "get",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            //Authorization: `Bearer ${token}`,
        },
        //body: `?pageNumber=${page}&pageSize=${size}`
    })
        .then((res) => {
            return res.json();
        })
        .then((answer: TAnswer) => {
            if (answer.success) {
                boundAdverts.setAdverts(answer.payload);
                return answer.payload;
            }
            else {
                if (answer.message === "Token Expired") {
                    alert("Token Expired. Logging out!");
                    localStorage.removeItem("token");
                    boundUser.logout();
                } else {
                    return Promise.reject(answer.payload);
                }
            }

        });
};

export const getAdvert = async (id: number) => {
    const token = localStorage.getItem("token");
    return await fetch(`${API_URL}/adverts/1`, {
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
                boundAdverts.setAdvert(answer.payload);
                return answer.payload;
            } else {
                //if (answer.message === "Token Expired") {
                //    alert("Token Expired. Logging out!");
                //    localStorage.removeItem("token");
                //    boundUser.logout();
                //} else {
                //    return Promise.reject(answer.payload);
                //}
                return Promise.reject(answer.payload);
            }
        });
};