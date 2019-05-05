# semantix
Desafio Engenheiro de Dados

Fonte: https://spark.apache.org/docs/latest/sql-getting-started.html

OBS.: O arquivo (ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz) é protegito por senha, não consegui fazer o download.

PERGUNTAS e RESPOSTAS
Qual o objetivo do comando cache em Spark?<br>
Evitar o reprocessamento de um conjunto de dados processado anteriormente, ou seja, o Spark irá deixar os dados disponíveis evitando utilização de recurso a cada chamada.

O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em MapReduce. Por quê?<br>
O MapReduce é eficiente para cálculo de processamento único, mas quando se trata de cálculo com várias execuções. Isto porque os resultados de saíde devem ser armazenados nos sistema de arquivos distribuídos, o que torna o processamento lento. Agora o código implementado em Spark suporta o compartilhamento de dados na memória, além de permitir a segmentação de passos, não sendo necessário criar uma nova fase Map e Reduce para cada cálculo.

Qual é a função do SparkContext?<br>
SparkContext é onde podemos criar ou fazer as configurações necessárias via o SparkConf, e definir os detalhes para o processameto das rotinas. Além disso, ele que faz a interação com os Cluster Spark.

Explique com suas palavras o que é Resilient Distributed Datasets(RDD).<br>
É um conjunto de dados criados e/ou transformados a partir de uma ou mais fone de dados.

GroupByKey é menos eficiente que reduceByKey em grandes dataset. Por quê?<br>
O reduceByKey é capaz de tratar/classificar os dados a serem enviados para uma execução antes de enviá-los, isto faz com que o trafego de informações seja menor do que quando usamos o groupByKey. Isto porque o groupByKey não trata/classifica os dados antes do envio ao executor.


Explique o que o código Scala abaixo faz.<br>
val textFile = sc.textFile("hdfs://...")<br>
val counts = textFile.flatMap(line=>line.split(" "))<br>
.map(word =>(word, 1))<br>
.reduceByKey(_+_)<br>
counts.saveAsTextFile("hdfs://...")<br>

1-Abre o arquivo
2-Faz o split(separa as palavras) da linha por " "(espaço)
3-Faz a classificação das pavras
4-Retorna o resultado/total de palavrs agrupadas.
