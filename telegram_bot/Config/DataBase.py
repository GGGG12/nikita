import re

import cur as cur
import numpy as np
import psycopg2
from numpy import char

from psycopg2 import Error


def CheckBalance(id_user):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"
        )
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(
                f"select balance from users where id_telegram = '{id_user}'"

            )

            result = cursor.fetchone()[0]

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")
    return result


def setUserProfile(id_users, name_users):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(

                f"INSERT INTO users (id_telegram,first_name,balance) VALUES ({id_users},'{name_users}',0);"
            )

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")


def getCategory():
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        data = []
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(

                "SELECT DISTINCT category from product"
            )
            rows = cursor.fetchall()
            for row in rows:
                data.append(str(row[0]))

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")

    return data


def getSingalQuery(message):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(
                message
            )
            str = cursor.fetchone()[0]
    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")

    return str


def getMultiQuery(message):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        data = []
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(

                message
            )
            rows = cursor.fetchall()
            for row in rows:
                data.append(str(row))

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")

    return data


def UpdateBalance(message):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        data = []
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(

                message
            )

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")


def UpdateAmount(message):
    try:
        connection = psycopg2.connect(
            database="postgres",
            user="postgres",
            password="pwks6643",
            host="127.0.0.1",
            port="5432"

        )
        data = []
        connection.autocommit = True
        with connection.cursor() as cursor:
            cursor.execute(

                message
            )

    except (Exception, Error) as error:
        print("Ошибка при работе с PostgreSQL", error)
    finally:
        if connection:
            # cursor.close()
            connection.close()
            print("Соединение с PostgreSQL закрыто")


# def getProduct():
#     try:
#         connection = psycopg2.connect(
#             database="postgres",
#             user="postgres",
#             password="pwks6643",
#             host="127.0.0.1",
#             port="5432"
#         )
#         connection.autocommit = True
#         with connection.cursor() as cursor:
#             cursor.execute(
#                 f"select product_name,price,amount from product"
#
#             )
#             print(cursor.fetchall())
#             rows = cursor.fetchall()
#             l = []
#             for row in rows:
#                 for x in row:
#                     l.append(x)
#
#     except (Exception, Error) as error:
#         print("Ошибка при работе с PostgreSQL", error)
#     finally:
#         if connection:
#             # cursor.close()
#             connection.close()
#             print("Соединение с PostgreSQL закрыто")
#
#     return l

# print(getMultiQuery("SELECT description,price,amount from product where category ='Яндекс'"))

tempAllValue = getMultiQuery(f"SELECT description,price,amount from product"
                             f" where category ='UBER'")
test = []
for items in range(0, len(tempAllValue)):
    test.append(tempAllValue[items].split(','))
res1, res2, res3 = map(list, zip(*test))
for items in range(0, len(res1)):
    res1[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", res1[items])
    res1[items] = res1[items].strip()
for items in range(0, len(res2)):
    res2[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", res2[items])
    res2[items] = res2[items].strip()
for items in range(0, len(res3)):
    res3[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", res3[items])
    res3[items] = res3[items].strip()
# print(res1[0])

# print(getSingalQuery(f"select category from product where description = 'Скидка 100 рублей Uber'"))
