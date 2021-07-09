1) 1.1)package CSVManager - пакет чтения и записи данных в csv файл,
   1.2)package db - пакет записи в БД данных считанных из файла csv,
   1.3)package entities - есть файл Instrument.java, для того чтобы установить и взять данные которые   надо записать в БД,
   1.4)packet utils - производится конвертация данных,
   1.5)ArgumentsReader.java - устанавливается путь до файла csv
 
 2)Путь до файла с транзакциями: src/main/resources/transactions_current_datetime.csv 
   Путь до файла с ценами: src/main/resources/price_file_datestamp.csv
 
 3)Если возникнут проблемы с установкой пути к файлу.
   В Inellij IDEA, найдите (нажмите) Run/Debug Configurations и укажите путь к файлу в Program     arguments.
 
 4)При запуске происходит чтение csv файлов и запись в БД
 
 
 
