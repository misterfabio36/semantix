package br.com.semantix;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
//https://spark.apache.org/docs/latest/sql-getting-started.html
public class Desafio {
	public static void main(String[] args) {
	
		String logFile = "/sysroot/home/fabio/projetos/java/data"; // Should be some file on your system
	    SparkSession spark = SparkSession.builder().appName("Simple Application").config("spark.master", "local").getOrCreate();
		JavaRDD<Data> peopleRDD = spark.read()
		  .textFile(logFile)
		  .javaRDD()
		  .map(line -> {
		    String[] parts = line.split(" ");
		    Data data = new Data(line);
		    data.setHost(parts[0]);
		    		    
		    Matcher matcherData = Pattern.compile("\\[(.*?):").matcher(line);
		    if (matcherData.find()){
		    	SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MMM/yyyy");
			    data.setData(new Date(dtFormat.parse(matcherData.group(1)).getTime()));
		    }
		    
		    Matcher matcherRequisicao = Pattern.compile("\"(.*?)\"").matcher(line);
		    if (matcherRequisicao.find()){
		    	data.setRequisicao(matcherRequisicao.group(1));
		    }
		    
		    Matcher matcherTotalBytes = Pattern.compile("\"\\s(.*?)$").matcher(line);
		    if (matcherTotalBytes.find()){
		    	if(NumberUtils.isNumber(matcherTotalBytes.group(1).split(" ")[0])) {
		    		data.setCodigoHTTP(Integer.parseInt(matcherTotalBytes.group(1).split(" ")[0]));
		    	}
		    	if(NumberUtils.isNumber(matcherTotalBytes.group(1).split(" ")[1])) {
		    		data.setTotalBytes(Long.parseLong(matcherTotalBytes.group(1).split(" ")[1]));
		    	}
		    }
		    
		    return data;
		  });
		
		Dataset<Row> dataDF = spark.createDataFrame(peopleRDD, Data.class);
		
		dataDF.createOrReplaceTempView("data");
		dataDF.show();
		
		numeroHostsUnicos(spark);
		
		totalErros404(spark);
		
		os5URLs​Mais​Erro​404(spark);
		
		quantidadeErros404Dia(spark);
		
		totalBytes​Retornados(spark);
	}

	/**
	 * Número​ ​ de​ ​ hosts​ ​ únicos.
	 * @param spark
	 * 
	 * +--------+
	 * |count(1)|
	 * +--------+
	 * |   81983|
	 * +--------+
	 */
	private static void numeroHostsUnicos(SparkSession spark) {
		Dataset<Row> numeroHostsUnicos = spark.sql("select count(*) from (SELECT host FROM data group by host)");
		numeroHostsUnicos.show();
	}

	/**
	 * O​ ​ total​ ​ de​ ​ erros​ ​ 404.
	 * @param spark
	 * 
	 * +--------+
	 * |count(1)|
	 * +--------+
	 * |   10833|
	 * +--------+
	 */
	private static void totalErros404(SparkSession spark) {
		Dataset<Row> totalErros404 = spark.sql("select count(*) from data where codigoHTTP = 404");
		totalErros404.show();
	}

	/**
	 * Os​ ​ 5 ​ ​ URLs​ ​ que​ ​ mais​ ​ causaram​ ​ erro​ ​ 404.
	 * @param spark
	 * 
	 * +-----+--------------------+
	 * |total|          requisicao|
	 * +-----+--------------------+
	 * |  667|GET /pub/winvn/re...|
	 * |  547|GET /pub/winvn/re...|
	 * |  286|GET /history/apol...|
	 * |  230|GET /history/apol...|
	 * |  230|GET /shuttle/reso...|
	 * +-----+--------------------+
	 */
	private static void os5URLs​Mais​Erro​404(SparkSession spark) {
		Dataset<Row> os5URLs​Mais​Erro​404 = spark.sql("select count(*) total, requisicao from data where codigoHTTP = 404 group by requisicao");
		os5URLs​Mais​Erro​404.orderBy(org.apache.spark.sql.functions.col("total").desc()).show(5);
	}

	/**
	 * Quantidade​ ​ de​ ​ erros​ ​ 404​ ​ por​ ​ dia
	 * @param spark
	 * 
	 * +----------+-----+
	 * |      data|total|
	 * +----------+-----+
	 * |1995-07-06|  640|
	 * |1995-07-19|  638|
	 * |1995-07-07|  569|
	 * |1995-07-13|  531|
	 * |1995-07-05|  497|
	 * |1995-07-11|  471|
	 * |1995-07-12|  470|
	 * |1995-07-03|  470|
	 * |1995-07-18|  465|
	 * |1995-07-25|  461|
	 * |1995-07-20|  428|
	 * |1995-07-14|  411|
	 * |1995-07-17|  406|
	 * |1995-07-10|  398|
	 * |1995-07-04|  359|
	 * |1995-07-09|  348|
	 * |1995-07-26|  336|
	 * |1995-07-27|  336|
	 * |1995-07-21|  332|
	 * |1995-07-24|  328|
	 * +----------+-----+
	 */
	private static void quantidadeErros404Dia(SparkSession spark) {
		Dataset<Row> quantidadeErros404Dia = spark.sql("select data, count(*) total from data where codigoHTTP = 404 group by data");
		quantidadeErros404Dia.orderBy(org.apache.spark.sql.functions.col("total").desc()).show();
	}

	/**
	 * O​ ​ total​ ​ de​ ​ bytes​ ​ retornados.
	 * @param spark
	 * 
	 * +---------------+
	 * |sum(totalBytes)|
	 * +---------------+
	 * |    38695978742|
	 * +---------------+
	 */
	private static void totalBytes​Retornados(SparkSession spark) {
		Dataset<Row> totalBytes​Retornados = spark.sql("select sum(totalBytes) from data");
		totalBytes​Retornados.show();
	}
}
