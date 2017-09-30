package org.lamb.lambframework.core.adapter.response;

import org.lamb.lambframework.core.aspect.config.JsonSymbolicFinalConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by WangGang on 2017/7/4 0004.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */

@Component
@Scope("prototype")
public class JsonResponser {




            private final String startPrefix = "{";

            private final String endPrefix = "}";

            private final String symbol = ":";

            private final String separate = ",";


            private boolean isbusinessResponse = false;

            private boolean isbusinessData = false;

            private String serviceCode;

            private String serviceMessage;

            private String businessCode;

            private String businessMessage;

            private String businessData;

            public JsonResponser setServiceResponseBody(String serviceCode, String serviceMessage){
                            this.serviceCode = serviceCode;
                            this.serviceMessage = serviceMessage;
                            return this;
            }

            public JsonResponser setBusinessResponseBody(String businessCode, String businessMessage){
                            this.businessCode= businessCode;
                            this.businessMessage= businessMessage;
                            this.isbusinessResponse= true;
                            return this;
            }

            public JsonResponser setBusinessData(String businessData){
                            this.businessData=businessData;
                            this.isbusinessResponse= true;
                            this.isbusinessData=true;
                            return this;
            }


            public String process(){
                StringBuffer buffer = new StringBuffer();
                buffer.append(startPrefix);
                buffer.append(setHead("service_response")).append(symbol).append(startPrefix);
                             buffer.append(setBody("service_code",getServiceCode())).append(separate);
                             buffer.append(setBody("service_message",getServiceMessage()));

                            if(isbusinessResponse){
                                           buffer.append(separate);
                                           buffer.append(setHead("business_response")).append(symbol).append(startPrefix);
                                                         buffer.append(setBody("business_code",getBusinessCode())).append(separate);
                                                         buffer.append(setBody("business_message",getBusinessMessage()));
                                                         if(isbusinessData){
                                                                       buffer.append(separate);
                                                                       String data = setData("businessData",getBusinessData());
                                                                       buffer.append(data);
                                                         }
                                           buffer.append(endPrefix);
                            }
                buffer.append(endPrefix);
                buffer.append(endPrefix);
                return buffer.toString();
            }




            private String setHead(String head){
                return "\""+head+"\"";
            }
            private String setBody(String key,String value){
                return "\""+key+"\""+symbol+"\""+value+"\"";
            }

            private String setData(String key,String value){
                value.replace("\\","\\");
                return "\""+key+"\""+symbol+""+value;
            }


            private String getBusinessData() {
                return businessData;
            }

            private String getServiceCode() {
                if(StringUtils.isBlank(serviceCode)){
                    serviceCode = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_CODE;
                }
                return serviceCode;
            }

            private String getBusinessCode() {
                if(StringUtils.isBlank(businessCode)){
                    businessCode = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_BUSINESS_CODE;
                }
                return businessCode;
            }

            private String getServiceMessage() {
                if(StringUtils.isBlank(serviceMessage)){
                    serviceMessage = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_MESSAGE;
                }
                return serviceMessage;
            }

            private String getBusinessMessage() {
                if(StringUtils.isBlank(businessMessage)){
                    businessMessage = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_BUSINESS_MESSAGE;
                }
                return businessMessage;
            }



            /*{
            service_response {
                            "service_code":"1000",                      //不能为空
                            "service_message":"接口调用成功"            //不能为空

                    business_response {
                                        "business_code":"10000"                     //不能为空
                                        "business_message":"业务处理成功"           //不能为空
                                        "business_data";{

                                                        }
                            }
            },

    }*/



    /*{
            service_response {
                            "service_code":"1000",                      //不能为空
                            "service_message":"接口调用成功"            //不能为空

                    business_response {
                                        "business_code":"10001"                     //不能为空
                                        "business_message":"实名认证失败"           //不能为空
                            }
            },

    }*/


    /*{
            service_response {
                            "service_code":"1000",                      //不能为空
                            "service_message":"接口调用失败"            //不能为空
            },

    }*/
}
