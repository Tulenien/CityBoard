import {FC} from "react";
import {Adverts} from "../models";
import styles from "./card.module.css";

interface CardProps {
    advert: Adverts;
}

const Card: FC<CardProps> = ({ advert }) => {

    return (
            <div className={styles.cardWrapper}>
                <div className={styles.cardHeader}>
                    Объявление № {advert.id}
                </div>
                <div className={styles.cardContent}>
                    Город: {advert.city}
                </div>
                <div className={styles.cardContent}>
                    Улица: {advert.street}
                </div>
                <div className={styles.cardContent}>
                    Район:{advert.district}
                </div>

                <div className={styles.cardContent}>
                    Этаж/этажи: {advert.floor}/{advert.floors}
                </div>
                <div className={styles.cardContent}>
                    Цена: {advert.price}
                </div>
                <div className={styles.cardContent}>
                    Описание: {advert.description}
                </div>
            </div>
    );
};
export { Card };