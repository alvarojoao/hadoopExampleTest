# hadoopExampleTest

## Simples Uso do hadoop no processamento de uma amostra de dados

Nesta amostra, no formato **csv**, encontram-se aproximadamente **20 milhões** de respostas à requisições de localização. Cada linha consiste no
seguinte schema:

        `"<mad_id>, <country>, <lat>, <lng>, <timestamp>, <source>"`

onde:

**mad_id**: identificador único do usuário;<br/>
**country**: sigla do país de origem da requisição. Pode ser **"MX"**, **"US"** ou **"CA"**;<br/>
**lat**: latitude do ponto geográfico da requisição;<br/>
**lng**: longitude do ponto geográfico da requisição;<br/>
**timestamp**: timestamp do momento da requisição, formato: "EEE MMM d HH:mm:ss zzz yyyy" (ex: "Thu Mar 24 14:08:08
BRT 2016")<br/>
**source**: tecnologia da rede pela qual localização foi identificada. Assume valores **"gps"** ou **"wifi"** nesta amostra.<br/>

Foi utilizado o **Hadoop** programado com **Java** para responder às seguintes questões:

1. Qual o número de usuários distintos por país? <br/>
  [DistinctUsersPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/DistinctUsersPerCountryJob.java)<br/>
  [DistinctUsersPerCountryJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/DistinctUsersPerCountryJob.txt)<br/>
2. Qual a hora do dia com mais requisições, em qualquer país?<br/>
  [PeakHourOfDayJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/PeakHourOfDayJob.java)<br/>
  [PeakHourOfDayJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/PeakHourOfDayJob.txt)<br/>
3. Qual o mad_id com mais requisições por país? <br/>
  [MostActiveUserPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/MostActiveUserPerCountry.java)<br/>
  [MostActiveUserPerCountryJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/MostActiveUserPerCountry.txt)<br/>
4. Quantas requisições são feitas em média por hora por país? <br/>
  [AverageHourlyRequestsCountPerCountryJob.java](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/AverageHourlyRequestsCountPerCountryJob.java)<br/>
  [AverageHourlyRequestsCountPerCountryJob.txt](https://github.com/alvarojoao/hadoopExampleTest/blob/master/src/results/AverageHourlyRequestsCountPerCountryJob.txt)<br/>


