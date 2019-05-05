package br.com.semantix;

import java.sql.Date;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
	/**
	 * Linha
	 */
	private String linha;
	
	/**
	 * Host fazendo a requisição​ . Um hostname quando possível, caso contrário o endereço de internet se o nome não​ ​ puder​ ​ ser​ ​ identificado.
	 */
	private String host;
	
	/**
	Timestamp​ ​ no​ ​ formato​ ​ "DIA/MÊS/ANO:HH:MM:SS​ ​ TIMEZONE"
	*/
	private Date data;
	
	/**
	Requisição​ ​ (entre​ ​ aspas)
	*/
	private String requisicao;
	
	/**
	Código​ ​ do​ ​ retorno​ ​ HTTP
	*/
	private Integer codigoHTTP;
	
	/**
	Total​ ​ de​ ​ bytes​ ​ retornados
	*/
	private Long totalBytes;
	
	public Data() {
		// TODO Auto-generated constructor stub
	}

	public Data(String linha) {
		super();
		this.linha = linha;
	}



	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(String requisicao) {
		this.requisicao = requisicao;
	}

	public Integer getCodigoHTTP() {
		return codigoHTTP;
	}

	public void setCodigoHTTP(Integer codigoHTTP) {
		this.codigoHTTP = codigoHTTP;
	}

	public Long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(Long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	public static void main(String[] args) throws ParseException {

	    
	    //01/Jul/1995:00:22:43
	    
	    		    
	    Matcher matcherData = Pattern.compile("\"\\s(.*?)$").matcher("mizzou-ts3-03.missouri.edu - - [01/Jul/1995:03:05:40 -0400] \"GET /shuttle/missions/sts-67/images/images.html   HTTP/1.0\" 200 4464");
	    if (matcherData.find()){
	    	/*SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss");
		    System.out.println(new Date(dtFormat.parse(matcherData.group(1)).getTime()));*/
	    	System.out.println(matcherData.group(1).split(" ")[1]);
	    }
	}
}
