package org.example.project_constants;

public class DialogPaneMessage {
    public static final String WRONG_MONTH_ERROR = """
            Произошла ошибка.
            Неверно указан МЕСЯЦ
            или
            названия столбцов таблицы в google списке заполнены неверно.
            """;

    public static final String NEW_FILE_ABSENCE_NOTIFICATION = """
            Нет файлов для записи. Если новые файлы были созданы, возможно вы забыли убрать знак "+",
            который помечает их как "уже добавленные в список"
            """;

    public static final String WRONG_FILE_NAME = "Имя файла:\n" + "%s" + "\n" + """ 
                                    записано с ошибкой.
                                    Проверьте наличие знаков "=" между данными о перевозке.
                                    """;
}
