# hadoopExampleTest

## Simples Uso do hadoop no processamento de uma amostra de dados

Nesta amostra, no formato **csv**, encontram-se aproximadamente **20 milhões** de respostas à requisições de localização. Cada linha consiste no
seguinte schema:

`"<mad_id>, <country>, <lat>, <lng>, <timestamp>, <source>"`

onde:

**mad_id**: identificador único do usuário;
**country**: sigla do país de origem da requisição. Pode ser "MX", "US" ou "CA";
**lat**: latitude do ponto geográfico da requisição;
**lng**: longitude do ponto geográfico da requisição;
**timestamp**: timestamp do momento da requisição, formato: "EEE MMM d HH:mm:ss zzz yyyy" (ex: "Thu Mar 24 14:08:08
BRT 2016")
**source**: tecnologia da rede pela qual localização foi identificada. Assume valores "gps" ou "wifi" nesta amostra.

Foi utilizado o **Hadoop** programado com **Java** para responder às seguintes questões:

1. Qual o número de usuários distintos por país? 
  [DistinctUsersPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/DistinctUsersPerCountryJob.java)
  [DistinctUsersPerCountryJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/DistinctUsersPerCountryJob.txt)
2. Qual a hora do dia com mais requisições, em qualquer país?
  [PeakHourOfDayJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/PeakHourOfDayJob.java)
  [PeakHourOfDayJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/PeakHourOfDayJob.txt)
3. Qual o mad_id com mais requisições por país? 
  [MostActiveUserPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/MostActiveUserPerCountryJob.java)
  [PeakHourMostActiveUserPerCountryJobOfDayJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/MostActiveUserPerCountryJob.txt)
4. Quantas requisições são feitas em média por hora por país? 
  [AverageHourlyRequestsCountPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/AverageHourlyRequestsCountPerCountryJob.java)
  [AverageHourlyRequestsCountPerCountryJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/AverageHourlyRequestsCountPerCountryJob.txt)


