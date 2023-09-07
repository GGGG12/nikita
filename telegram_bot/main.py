import re
from random import randint
from random import choice
from string import ascii_uppercase

from pyqiwip2p import QiwiP2P
import telebot
from aiogram.types import update

import Config.DataBase
from telebot import types
from telebot.types import InlineKeyboardButton, InlineKeyboardMarkup, ReplyKeyboardMarkup
import Config.QIWI

bot = telebot.TeleBot("5630768068:AAFsYhPIPA3EGVICDbZ7Wy8SVBpSOgafhOM")

product = []
nameD = 'h'

p2p = QiwiP2P(auth_key=Config.QIWI.token)


@bot.message_handler(commands=['start'])
def start(message):
    item1 = types.KeyboardButton('Товары в наличие')
    item2 = types.KeyboardButton('Купить')
    item3 = types.KeyboardButton('Профиль')
    item4 = types.KeyboardButton('Помощь')
    item5 = types.KeyboardButton('О боте')
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add(item1, item2, item3, item4, item5)
    name_user = f'Привет,{message.from_user.first_name}'
    bot.send_message(message.chat.id, name_user, reply_markup=markup)
    Config.DataBase.setUserProfile(message.chat.id, message.from_user.first_name)


@bot.message_handler(content_types=['text'])
def sendText(message):
    if message.chat.type == 'private':
        if message.text == 'Товары в наличие':
            # markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            # backMenu = types.KeyboardButton('Вернуться в главное меню')
            # markup.add(backMenu)
            valueAmount = Config.DataBase.getMultiQuery(f"SELECT promocode from product where description "
                                                        f"= 'Скидка 10 рублей десткий мир'")
            checkCheck = []
            checkCheck = Config.DataBase.getMultiQuery(f"SELECT promocode from product")
            testTest = Config.DataBase.getMultiQuery(f"SELECT promocode from product")
            print(testTest)
            testList = []
            for items in range(0, len(testTest)):
                testTest[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9\,]", " ", testTest[items])
                testTest[items] = testTest[items].strip()
                temp1 = testTest[items]
                # temp1 = temp1[:-1]
                # testTest[items] = temp1
                # testTest[items] = testTest[items].strip()
                # testList = testTest[items].split(',')
            print(f' print  {testTest}')
            print(f' print  {checkCheck}')

            promoList = []
            temp = ''
            for items in range(0, len(valueAmount)):
                valueAmount[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9\,]", " ", valueAmount[items])
                valueAmount[items] = valueAmount[items].strip()
                temp = valueAmount[items]
                temp = temp[:-1]
                valueAmount[items] = temp
                valueAmount[items] = valueAmount[items].strip()
                promoList = valueAmount[items].split(',')
            # print(valueAmount)
            # print(len(valueAmount))
            print(promoList)
            print(len(promoList))

            price = Config.DataBase.getMultiQuery(f"SELECT price from product")
            print(price)
            for items in range(0, len(price)):
                price[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", price[items])
                price[items] = price[items].strip()
            print(price)
            description = Config.DataBase.getMultiQuery(f"SELECT description from product")
            for items in range(0, len(description)):
                description[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", description[items])
                description[items] = description[items].strip()
            print(description)
            amount = Config.DataBase.getMultiQuery(f"SELECT amount from product")
            for items in range(0, len(amount)):
                amount[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", amount[items])
                amount[items] = amount[items].strip()
            print(amount)
            price = ['Цена: ' + i + ' р.' for i in price]
            amount = ['Кол-во: ' + i + ' шт.' for i in amount]
            optionsList = [*map(lambda x: f'{x[0]} | {x[1]} | {x[2]}', zip(price, description, amount))]
            category = Config.DataBase.getMultiQuery(f"SELECT category from product")
            for items in range(0, len(category)):
                category[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", category[items])
                category[items] = category[items].strip()
            print(amount)
            category = ['Категория : ' + i for i in category]
            testListItem = [*map(lambda x: f'{x[0]}  \n {x[1]}', zip(category, optionsList))]
            bot.send_message(message.chat.id, '\n'.join(map(str, testListItem)))

        elif message.text == 'Купить':
            product.clear()
            testListItem = Config.DataBase.getCategory()
            testListItem.insert(0, 'Вернуться в главное меню')
            markup = types.ReplyKeyboardMarkup(resize_keyboard=True)

            for items in range(0, len(testListItem)):
                product.append(testListItem[items])

            for items in range(0, len(product)):
                btns = types.KeyboardButton(f'{product[items]}')
                markup.add(btns)
            msg = bot.send_message(message.chat.id, 'Выберите категорию:', reply_markup=markup)
            bot.register_next_step_handler(msg, getDescription)

            # bot.send_message(message.chat.id, " Выбирете товар:", reply_markup=markup)

        elif message.text == 'Вернуться в главное меню':
            item1 = types.KeyboardButton('Товары в наличие')
            item2 = types.KeyboardButton('Купить')
            item3 = types.KeyboardButton('Профиль')
            item4 = types.KeyboardButton('Помощь')
            item5 = types.KeyboardButton('О боте')
            markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            markup.add(item1, item2, item3, item4, item5)
            bot.send_message(message.chat.id, 'Вы вернулись в главное меню', reply_markup=markup)

        elif message.text == 'Профиль':
            buttons = [[InlineKeyboardButton('Пополнить баланс', callback_data='Balance')]]
            numberOfPurchasec = 0
            bot.send_message(message.chat.id, f'Пользователь: {message.from_user.first_name}\n'
                                              f'Количество покупок: {numberOfPurchasec}\n'
                                              f'ID: {message.from_user.id}\n'
                                              f'Ваш баланс: {Config.DataBase.CheckBalance(message.from_user.id)}\n',
                             reply_markup=InlineKeyboardMarkup(buttons))

        elif message.text == 'Помощь':
            # markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            # backMenu = types.KeyboardButton('Вернуться в главное меню')
            # markup.add(backMenu)
            adminName = '@kapustinnikita'
            bot.send_message(message.chat.id, f'По всем вопросам писать создателю {adminName}')

        elif message.text == 'О боте':
            # markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            # backMenu = types.KeyboardButton('Вернуться в главное меню')
            # markup.add(backMenu)
            adminName = '@kapustinnikita'
            bot.send_message(message.chat.id, f'Бот создал {adminName} в учебных целях по дисциплине ИС на языке '
                                              f'программирования Python с помощью API Telebot')

    # else:
    #     bot.send_message(message.chat.id, "Воспользуйтесь клавиатурой")


def getDescription(message):
    category = []

    category = Config.DataBase.getMultiQuery("select DISTINCT category from product")
    for items in range(0, len(category)):
        category[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", category[items])
        category[items] = category[items].strip()
    print(category)
    print(message.text)
    print(category.__contains__(message.text))
    if category.__contains__(message.text):
        tempAllValue = Config.DataBase.getMultiQuery(f"SELECT description,price,amount from product"
                                                     f" where category ='{message.text}'")

        print(f'eto srabotalo titt{message.text}')
        print(tempAllValue)
        markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
        test = []
        res1 = []
        res2 = []
        res3 = []
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
        markup.add('Вернуться в главное меню')
        for items in range(0, len(res1)):
            markup.add(f'{res1[items]} | {res2[items]} р. | {res3[items]} шт.')
        msg = bot.send_message(message.chat.id, f'{message.text}', reply_markup=markup)
        bot.register_next_step_handler(msg, printDescriptionProduct)
    elif message.text == 'Вернуться в главное меню':
        item1 = types.KeyboardButton('Товары в наличие')
        item2 = types.KeyboardButton('Купить')
        item3 = types.KeyboardButton('Профиль')
        item4 = types.KeyboardButton('Помощь')
        item5 = types.KeyboardButton('О боте')
        markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
        markup.add(item1, item2, item3, item4, item5)
        bot.send_message(message.chat.id, 'Вы вернулись в главное меню', reply_markup=markup)


def printDescriptionProduct(message: types.Message):
    if message.text == 'Вернуться в главное меню':
        item1 = types.KeyboardButton('Товары в наличие')
        item2 = types.KeyboardButton('Купить')
        item3 = types.KeyboardButton('Профиль')
        item4 = types.KeyboardButton('Помощь')
        item5 = types.KeyboardButton('О боте')
        markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
        markup.add(item1, item2, item3, item4, item5)
        bot.send_message(message.chat.id, 'Вы вернулись в главное меню', reply_markup=markup)
    else:
        global_dictionary("", "", "", "clear", message.from_user.id)
        nameD = ''
        temp = message.text
        nameDescription = temp.partition('|')[0].strip()
        nameD = nameDescription
        print(f' etetet {nameD}')
        global_dictionary("text", nameD, "", "add", message.from_user.id)
        nameAmount = Config.DataBase.getSingalQuery(f"select amount from product where description ='"
                                                    f"{nameDescription}'")
        markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
        markup.add('Вернуться в главное меню')
        nameCategory = Config.DataBase.getSingalQuery(f"select category from product where description ='"
                                                      f"{nameDescription}'")
        bot.send_message(message.chat.id, f'Категория: {nameCategory}\n'
                                          f'Описание: {nameDescription}\n'
                                          f'В наличие: {nameAmount} шт.'
                         , reply_markup=markup)
        keyboard = InlineKeyboardMarkup(row_width=2)
        inline_btn_1 = InlineKeyboardButton('1', callback_data='1')
        inline_btn_2 = InlineKeyboardButton('2', callback_data='2')
        inline_btn_3 = InlineKeyboardButton('5', callback_data='5')
        inline_btn_4 = InlineKeyboardButton('10', callback_data='10')
        keyboard.row(inline_btn_1, inline_btn_2, inline_btn_3, inline_btn_4)
        bot.send_message(message.chat.id, 'Выбирте нужное количество: ', reply_markup=keyboard)


def takeMoney(message: types.Message):
    if message.text.isdigit():
        message_money = int(message.text)
        if message_money >= 5:
            global_qiwi("", "", "", "clear", message.from_user.id)
            comment = str(message.from_user.id) + "_" + str(randint(1000, 9999))
            bill = p2p.bill(amount=message_money, lifetime=15, comment=comment)
            #global_dictionary("text", bill, "", "add", message.from_user.id)
            #global_dictionary("text", message_money, "", "add", message.from_user.id)
            global_qiwi("text", bill, "", "add", message.from_user.id)
            global_qiwi("text", message_money, "", "add", message.from_user.id)
            keyboard = InlineKeyboardMarkup(row_width=2)
            inline_btn_1 = InlineKeyboardButton('Карта', url=bill.pay_url)
            inline_btn_2 = InlineKeyboardButton('Проверить пополнение', callback_data='checkPayment')
            keyboard.row(inline_btn_1)
            keyboard.row(inline_btn_2)
            # markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            # markup = types.InlineKeyboardMarkup(row_width=1).add(
            #     types.InlineKeyboardButton('Ссылка на оплату', url=bill.pay_url))
            bot.send_message(message.chat.id, 'Выберите способ пополнения', reply_markup=keyboard)

            # print(f'its BILL {bill}')
            # print(str(p2p.check(bill_id=bill).status))
            # if str(p2p.check(bill_id=bill).status) == "PAID":
            #     Config.UpdateBalance(f"update users set balance = '{message_money}' where id_telegram = "
            #                          f"'{message.from_user.id}'")
            #     bot.send_message(message.chat.id, 'Баланс пополнен')


        # bot.send_message(message.from_user.id, f'Ссылка {bill.pay_url}')
        else:

            bot.send_message(message.chat.id,
                             'Минимальная сумма пополнения 5 рублей')
            bot.send_message(message.chat.id, "Введите сумму пополнения еще раз")
            bot.register_next_step_handler(message, takeMoney)
    else:
        if message.text == 'Вернуться в главное меню':
            item11 = types.KeyboardButton('Товары в наличие')
            item22 = types.KeyboardButton('Купить')
            item33 = types.KeyboardButton('Профиль')
            item44 = types.KeyboardButton('Помощь')
            item55 = types.KeyboardButton('О боте')
            markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
            markup.add(item11, item22, item33, item44, item55)
           # bot.send_message(message.chat.id, ' ', reply_markup=markup)
        else:
            item1 = types.KeyboardButton('Товары в наличие')
            item2 = types.KeyboardButton('Купить')
            item3 = types.KeyboardButton('Профиль')
            item4 = types.KeyboardButton('Помощь')
            item5 = types.KeyboardButton('О боте')
            markupMenu = types.ReplyKeyboardMarkup(resize_keyboard=True)
            markupMenu.add(item1, item2, item3, item4, item5)
            bot.send_message(message.chat.id, "Некоректный ввод\nВведите сумму пополнения еще раз")
            bot.register_next_step_handler(message, takeMoney)


@bot.callback_query_handler(func=lambda call: call.data == 'checkPayment')
def checkPayment(call):
    #global_qiwi("", "", "", "clear", call.from_user.id)
    bill = global_qiwi("", "", "", "check", call.from_user.id)[0]
    message_money = global_qiwi("", "", "", "check", call.from_user.id)[1]
    print(f'NOW OTRABOTALO bill =  {bill}')
    print(f'NOW OTRABOTALO money =  {message_money}')
    bot.answer_callback_query(call.id)
    if str(p2p.check(bill_id=bill).status) == 'PAID':
        Config.DataBase.UpdateBalance(f"update users set balance = '{message_money}' where id_telegram = "
                                      f"'{call.from_user.id}'")
        bot.send_message(call.from_user.id, 'Баланс пополнен')
    else:
        bot.send_message(call.from_user.id, ' Вы не оплатили счёт!')

    #global_qiwi("", "", "", "clear", call.from_user.id)


@bot.callback_query_handler(func=lambda c: c.data == 'Balance')
def Balance(c):
    bot.answer_callback_query(c.id)
    bot.send_message(c.from_user.id, 'Введите сумму пополнения')
    bot.register_next_step_handler(c.message, takeMoney)


get_data = []


def global_dictionary(types, data, caption, method, login):
    if method == "add":
        if types == "text":
            # get_data.append(types)
            get_data.append(data)
            # get_data.append(method)
        else:
            get_data.append(types)
            get_data.append(data)
            get_data.append(caption)
            get_data.append(method)
    elif method == "clear":
        get_data.clear()
    elif method == "check":
        return get_data

def global_qiwi(types, data, caption, method, login):
    if method == "add":
        if types == "text":
            # get_data.append(types)
            get_data.append(data)
            # get_data.append(method)
        else:
            get_data.append(types)
            get_data.append(data)
            get_data.append(caption)
            get_data.append(method)
    elif method == "clear":
        get_data.clear()
    elif method == "check":
        return get_data


@bot.callback_query_handler(func=lambda call: True)
def ChoiceOfQuantityOfGoodsForPayment(call):
    if call == '1' or '2' or '5' or '10':
        msg = global_dictionary("", "", "", "check", call.from_user.id)
        print(f'message = {msg}')
        amount = Config.DataBase.getSingalQuery(f"select amount from product where description ='{msg[0]}'")
        tempPrice = Config.DataBase.getSingalQuery(f"select price from product where description ='{msg[0]}'")
        print(f'amount = {amount}')
        PriceProduct: int
        oneValue = int(call.data)
        secondValue = int(tempPrice)
        priceProduct = oneValue * secondValue
        if call.data <= amount:
            # print(call.data)
            # print(tempPrice)
            # print(priceProduct)
            bot.answer_callback_query(call.id)
            balance = Config.DataBase.CheckBalance(call.from_user.id)
            comment = str(call.from_user.id) + "_" + str(randint(1000, 9999))
            if int(balance) >= priceProduct:

                # promo = Config.DataBase.getMultiQuery(f"select promocode from product where  description ='{msg[0]}'")
                # for items in range(0, len(promo)):
                #     promo[items] = re.sub("[^A-Za-z0-9-А-Яа-я0-9]", " ", promo[items])
                #     promo[items] = promo[items].strip()
                amount = Config.DataBase.getSingalQuery(f"select amount from product where description ='{msg[0]}'")
                oneValue = int(call.data)
                secondValue = int(amount)
                amount = secondValue - oneValue
                promoList = []
                for items in range(0, oneValue):
                    promoList.append(''.join(choice(ascii_uppercase) for i in range(12)))
                msg = global_dictionary("", "", "", "check", call.from_user.id)
                result = balance - priceProduct
                Config.DataBase.UpdateBalance(f"update users set balance = '{result}' where id_telegram = "
                                              f"'{call.from_user.id}'")
                Config.DataBase.UpdateAmount(f"update product set amount = '{amount}' where description ='{msg[0]}'")
                bot.send_message(call.from_user.id, f'Ваш баланс: {balance - priceProduct} рублей \nСпасибо за покупку')
                bot.send_message(call.from_user.id, ' \n'.join(map(str, promoList)))

            else:
                balance = Config.DataBase.CheckBalance(call.from_user.id)
                msg = bot.send_message(call.from_user.id,
                                       f'На вашем балансе недостаточно средств, необходимо пополнить '
                                       f'на сумму: {priceProduct - balance} рублей.')
                # markup = types.InlineKeyboardMarkup(row_width=1).add(
                #     types.InlineKeyboardButton(text='Пополнить картой', callback_data='qwerty'))

                # buttons = [[InlineKeyboardButton('Пополнить баланс', callback_data='Balance')]]
                # bot.send_message(call.from_user.id, 'Выберите способ пополнения: ',
                #                  reply_markup=InlineKeyboardMarkup(buttons))
               # bot.send_message(call.from_user.id, 'Введите сумму пополнения: ')
                #global_dictionary("", "", "", "clear", call.from_user.id)
                #bot.register_next_step_handler(msg, takeMoney)
        else:
            bot.answer_callback_query(call.id)
            bot.send_message(call.from_user.id, 'Выбраное количество товара нет в наличии, выберите другое значение')


bot.polling(none_stop=True)
