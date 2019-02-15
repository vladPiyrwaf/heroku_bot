package vlad;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot  extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void  sendMsg(Message message, String text) {    //описывает что бот будет возвращать на наше сообщение
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);    //включаем возможность разметки
        sendMessage.setChatId(message.getChatId().toString());  //определяем id нашего чата
        sendMessage.setReplyToMessageId(message.getMessageId());    //определяем id нашего сообщения
        sendMessage.setText(text);

        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {       //для обновления бота
        Message message = update.getMessage();
        String txt = message.getText();
        if(txt.equals("/start")){
            sendMsg(message, "Привет, я показываю баллы на дорогах Самары – напиши '/Пробки'." + "\n"
                    + "Могу показать погоду в Самаре - напиши '/Погода'.");
        }
        if(message != null && message.hasText()) {
            switch (message.getText()) {
                case "/Что я умею":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/Пробки":
                    try {
                        sendMsg(message, ParsePlugs.getPlugs());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                 case "/Погода":
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

    public void setButtons(SendMessage sendMessage) {        // метод создает клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();    //клавиатура
        sendMessage.setReplyMarkup(replyKeyboardMarkup);    //разметка клавиатуры
        replyKeyboardMarkup.setSelective(true);     // клавиатура доступна всем
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/Что я умею"));
        keyboardFirstRow.add(new KeyboardButton("/Пробки"));
        keyboardFirstRow.add(new KeyboardButton("/Погода"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


    public String getBotUsername() {
        return "SamaraCityBot";
    }

    public String getBotToken() {
        return "493419600:AAEF9e8I3qXpC-UZgbpEs6B1Tc5fUE0q_to";
    }
}
