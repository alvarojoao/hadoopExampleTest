# hadoopExampleTest

## Simples Uso do hadoop no processamento de uma amostra de dados

Nesta amostra, no formato **csv**, encontram-se aproximadamente **20 milhões** de respostas à requisições de localização. Cada linha consiste no
seguinte schema:

        "<mad_id>, <country>, <lat>, <lng>, <timestamp>, <source>"

onde:

**mad_id**: identificador único do usuário;<br/>
**country**: sigla do país de origem da requisição. Pode ser **"MX"**, **"US"** ou **"CA"**;<br/>
**lat**: latitude do ponto geográfico da requisição;<br/>
**lng**: longitude do ponto geográfico da requisição;<br/>
**timestamp**: timestamp do momento da requisição, formato:  **"EEE MMM d HH:mm:ss zzz yyyy"** (ex: **"Thu Mar 24 14:08:08
BRT 2016"**)<br/>
**source**: tecnologia da rede pela qual localização foi identificada. Assume valores **"gps"** ou **"wifi"** nesta amostra.<br/>

Foi utilizado o **Hadoop** programado com **Java** para responder as seguintes questões:

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


## Como Executar o programa.

###Instalações

Foi usado o Haddop versão `hadoop-2.3.0`. <br/>
Nessa versão executamos os seguintes comandos:

1. `%HADOOP_PREFIX%\bin\hdfs namenode -format` <br/>
2. `%HADOOP_PREFIX%\sbin\start-dfs.cmd` <br/>
3. `%HADOOP_PREFIX%\sbin\start-yarn.cmd` <br/>

P.s: `%HADOOP_PREFIX%` é o caminho para a raiz do projeto [Hadoop 2.3.0](https://wiki.apache.org/hadoop/Hadoop2OnWindows).

###Upload da base [location_requests.csv](https://s3.amazonaws.com/ubee-public/data-samples/location_requests/north_america_sample.gz)

1. `hadoop fs -mkdir /in` <br/>
2. `hadoop fs -put location_requests.csv /in/location_requests.csv` <br/>

###Execução código java

Nese processo, usamos o [eclipse Kepler] (http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1) com o plugin  <br/> hadoop instalado para facilitar a execusão dos programas e a obtenção dos resultados.

P.s.: Foi utilizado o host `hdfs://127.0.0.1:9000/` 
