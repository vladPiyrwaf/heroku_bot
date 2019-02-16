package vlad;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot  extends TelegramLongPollingBot {
    public void  sendMsg(Message message, String text) {    //метод описывает что бот будет возвращать на наше сообщение
        SendMessage sendMessage = new SendMessage() // Create a message object
                .enableMarkdown(true)    //включаем возможность разметки
                .setChatId(message.getChatId().toString()) //определяем id нашего чата
                .setReplyToMessageId(message.getMessageId())    //определяем id нашего сообщения
                .setText(text);

        try {
            KeyBoard(sendMessage);
            execute(sendMessage);  //Sending our message object to user

        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    /*когда бот получает обновление (т.е. пользователь отправляет ему сообщение)
        , то вызывается метод onUpdateReceived*/
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String txt = message.getText();
        if(txt.equals("/start")){
            sendMsg(message, "Привет, я показываю баллы на дорогах Самары – напиши '/Пробки'." + "\n"
                    + "Могу показать погоду в Самаре - напиши '/Погода'.");
        }
        if(message != null && message.hasText()) {
            switch (message.getText()) {
                case "Что я умею":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "Пробки":
                    try {
                        sendMsg(message, ParsePlugs.getPlugs());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                 case "Погода":
                    try {
                        sendMsg(message, Samara_Weather.getWeather());
                    } catch (IOException e){
                        System.err.println("Неизвестная ошибка" + e);
                    }
                default:
                    try {
                    } catch (Exception e) {
                        sendMsg(message, "Команда не найдена");
                    }
            }
        }

    }

    public void KeyBoard(SendMessage message){
        // Creat ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList();
        // Create keyboard row
        KeyboardRow row = new KeyboardRow();
        row.add("Что я могу");
        row.add("Пробки");
        row.add("Погода");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

    }


    public String getBotUsername() {
        return "SamaraCityBot";
    }

    public String getBotToken() {
        return "493419600:AAEF9e8I3qXpC-UZgbpEs6B1Tc5fUE0q_to";
    }
}
