/*
 * 2007-2016 [PagSeguro Internet Ltda.]
 *
 * NOTICE OF LICENSE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright: 2007-2016 PagSeguro Internet Ltda.
 * Licence: http://www.apache.org/licenses/LICENSE-2.0
 */
package br.com.uol.pagseguro.api.exception;

import br.com.uol.pagseguro.api.http.HttpResponse;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The exception is thrown when a "bad request" occurs
 *
 * @author PagSeguro Internet Ltda.
 * @see PagSeguroServerException
 */
public class PagSeguroBadRequestException extends PagSeguroServerException {

  private static final long serialVersionUID = 1L;

  private final ServerErrors errors;

  /**
   * Constructor
   *
   * @param serverResponse Server response
   * @param errors         Server Errors
   * @see HttpResponse
   * @see ServerErrors
   */
  public PagSeguroBadRequestException(HttpResponse serverResponse, ServerErrors errors) {
    super(serverResponse);
    this.errors = errors;
  }

  /**
   * Get errors
   *
   * @return Errors
   * @see ServerErrors
   */
  public ServerErrors getErrors() {
    return errors;
  }
	
  /**
   * Get message errors
   *
   * @return message
   */
  public String getMessage(){
    String msg = "";
    try {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  	DocumentBuilder builder = factory.newDocumentBuilder();
  	InputSource is = new InputSource(new StringReader(getServerResponse().asString()));
  	Document doc = builder.parse(is);
  	NodeList errors = doc.getDocumentElement().getElementsByTagName("error");
  	for (int i = 0; i < errors.getLength(); i++)
  	{
  	   msg += errors.item(i).getLastChild().getTextContent() + " ";
  	}
    } catch (ParserConfigurationException e) {
  	e.printStackTrace();
    }catch (SAXException e) {
  	e.printStackTrace();
    }catch (IOException e) {
  	e.printStackTrace();
    }
    return msg;
  }
}
