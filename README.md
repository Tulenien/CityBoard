# iu7-software-design

# Software Design course. BMSTU 2022.

## 1. Название приложения
	Приложение для осуществления риелторской деятельности.

## 2. Идея приложения
	Создать приложение, использующее подключение к базе данных, для организации торговли квартирами и аренде квартир на вторичном рынке жилья.

## 3. Предметная область
	Вторичный рынок недвижимости состоит из квартир, которые были во владении одного или нескольких лиц. Понятие 'торговля квартирами' включает в себя продажу и покупку квартир, где в сделке учавствуют два лица, продавец или его полноправный представитель и покупатель соответственно. В случае аренды в сделке также принимают участие два лица: владелец недвижимости или его полноправный представитель и арендатор. 

## 4. Анализ аналогичных решений
	Обозначения:
- Критерий A -- Возможность оценить стоимость квартиры
- Критерий Б -- Возможность работы с объявлениями внутри приложения
- Критерий В -- Возможность запросить просмотр квартиры у владельца

| А    | Б    | В   | 
| :--: | :--: | :--:|
| ✅   | ❌   | ❌  |
| ✅   | ✅   | ❌  |
| ✅   | ✅   | ✅  |

## 5. Use-Case диаграмма
	Представлена в файле use_case.pdf.

## 6. ER-диаграмма сущностей
	Представлена в файле er_chen.pdf.

## 7. Архитектурные характеристики, важные для проекта
	Важными архитектурными характеристиками являются доступность, безопасность, простота.
