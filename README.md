Чтобы успешно запустить программу нужно установить: 
1 openjdk-8-jdk 
2 Cassandra 3.11.10
3 InelliJ IDEA

Если вы пользователь Linux Ubuntu 20.04 то дальше будет показано как это все можно установить 

Шаг 1. Во-первых, убедитесь, что все ваши системные пакеты обновлены, выполнив следующие aptкоманды в терминале.

  1.1)sudo apt update
  
  1.2)sudo apt upgrade
  
  1.3)sudo apt install apt-transport-https

Шаг 2. Установка Java.
 
  2.1)Выполните следующую команду, чтобы установить OpenJDK:
  
   sudo apt install openjdk-8-jdk
 
  2.2)Чтобы проверить, установлен и запущен ли OpenJDK, выполните следующие команды:
  
   java -version

Шаг 3. Загрузите и установите Apache Cassandra в Ubuntu 20.04.
  
  3.1)Теперь импортируем GPG-ключ репозитория и добавляем репозиторий Cassandra в систему:
    
    
    3.1.1)wget -q -O - https://www.apache.org/dist/cassandra/KEYS | sudo apt-key add -
    3.1.2)sudo sh -c 'echo "deb http://www.apache.org/dist/cassandra/debian 311x main" > /etc/apt/sources.list.d/cassandra.list'
 	
  3.2)После шагов, описанных выше, выполните следующие команды, чтобы установить его:
    
    3.2.1)sudo apt update
    
    3.2.2)sudo apt install cassandra
    
  3.3)Служба Apache Cassandra автоматически запустится после завершения процесса установки. Чтобы проверить, правильно ли установлена ​​Cassandra, выполните следующие команды:
    
    nodetool status

  3.4)Для взаимодействия с Cassandra через CQL (язык запросов Cassandra) вы можете использовать утилиту командной строки с именем cqlsh: 
  
  cqlsh
  
  4)Если возникнут проблемы с установкой пути к файлу.
   В Inellij IDEA, найдите (нажмите) Run/Debug Configurations и укажите путь к файлу в Program     arguments.
  
  
  
1) 1.1)package CSVManager - пакет чтения и записи данных в csv файл,
   1.2)package db - пакет записи в БД данных считанных из файла csv,
   1.3)package entities - есть файл Instrument.java, для того чтобы установить и взять данные которые   надо записать в БД,
   1.4)packet utils - производится конвертация данных,
   1.5)ArgumentsReader.java - устанавливается путь до файла csv
 
 2)Путь до файла с транзакциями: src/main/resources/transactions_current_datetime.csv 
   Путь до файла с ценами: src/main/resources/price_file_datestamp.csv

 
 4)При запуске происходит чтение csv файлов и запись в БД
 
 
 
