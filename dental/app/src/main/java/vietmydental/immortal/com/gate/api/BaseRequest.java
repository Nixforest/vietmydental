package vietmydental.immortal.com.gate.api;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.gate.utils.ErrorCode;

public abstract class BaseRequest extends AsyncTask {
    /** Request type */
    private int                 requestType             = 0;
    /** Response */
    private BaseResponse        response;
    /** Sub url */
    private String              subUrl                  = DomainConst.BLANK;

    /**
     * Constructor.
     * @param subURL Sub URL
     */
    protected BaseRequest(String subURL) {
        setSubUrl(subURL);
        setRequestType(DomainConst.REQUEST_POST);
    }

    /**
     * Get sub URL value.
     * @return Sub URL value
     */
    public String getSubUrl() {
        return subUrl;
    }

    /**
     * Set sub URL value.
     * @param subUrl Sub URL value
     */
    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    /**
     * Set request type.
     * @param requestType Request type value
     */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /**
     * Get response for request.
     *
     * @return Response value
     */
    public BaseResponse getResponse() {
        return response;
    }
    /**
     * Get body for request.
     *
     * @return Json string of body
     */
    protected abstract List<NameValuePair> buildParameter();

    /**
     * Build file parameter.
     *
     * @return Json string of body
     */
    protected Map<String, File> buildFileParameter() {
        return null;
    }

    /**
     * Generate default JSON object
     * @return
     */
    protected JSONObject generateDefaultJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put(DomainConst.KEY_PLATFORM, "1");
            object.put(DomainConst.KEY_VERSION_CODE, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Get server URL.
     *
     * @return Server URL
     */
    public static String getServerURL() {
        return BaseModel.getInstance().getServerURL();
    }

    /**
     * Connect with server in background.
     *
     * @param params List parameters
     * @return Object
     */
    @Override
    protected Object doInBackground(Object[] params) {
        List<NameValuePair> nameValuePairs = buildParameter();
        HttpEntity entity = null;
        try {
            if (nameValuePairs != null) {
                entity = new UrlEncodedFormEntity(nameValuePairs, DomainConst.CHARACTER_ENCODING_UTF8);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Log.e(DomainConst.LOG_TAG_ERROR,
                    DomainConst.LOG_MSG_HTTP_ACCESS_FAILED, ex);
        }

        Map<String, File> files = buildFileParameter();
        if ((files != null) && (files.size() > 0)) {
            Log.w(DomainConst.LOG_TAG_WARN,
                    DomainConst.LOG_MSG_GET_DATA_FROM_URL + BaseRequest.getServerURL());
            // TODO: Request upload files
        } else {
            Log.w(DomainConst.LOG_TAG_WARN,
                    DomainConst.LOG_MSG_GET_DATA_FROM_URL + BaseRequest.getServerURL());
            response = execute(BaseRequest.getServerURL() + getSubUrl(), requestType, entity);
        }

        // TODO: Remove optimizise images after request finish if possible

        return null;
    }

    /**
     * Execute request
     * @param url Url of request
     * @param requestType Type of request
     * @param entity Http entity
     * @return
     */
    protected BaseResponse execute(String url, int requestType, HttpEntity entity) {
        Log.w(DomainConst.LOG_TAG_WARN,
                DomainConst.LOG_MSG_GET_DATA_FROM_URL + url);
        BaseResponse resp = new BaseResponse();
        HttpUriRequest httpReq;
        try {
            switch (requestType) {
                case DomainConst.REQUEST_POST:
                    // Make post request
                    HttpPost httpPost = new HttpPost(url);

                    // Add your data and get response
                    httpPost.setEntity(entity);

                    // Set to request
                    httpReq = httpPost;
                    httpReq.setHeader(DomainConst.HTTP_REQ_HEADER, DomainConst.HTTP_REQ_HEADER_CONTENT);
                    break;
                case DomainConst.REQUEST_PUT:
                    // Make put request
                    HttpPut httpPut = new HttpPut(url);

                    // Add your data & get response
                    httpPut.setEntity(entity);

                    // Set to request
                    httpReq = httpPut;
                    break;
                case DomainConst.REQUEST_GET:
                    // Make get request
                    // Set to request
                    httpReq = new HttpGet(url);
                    break;
                case DomainConst.REQUEST_DELETE:
                    // Make get request
                    // Set to request
                    httpReq = new HttpDelete(url);
                    break;
                default:
                    // requestType is invalid
                    return resp;
            }

            HttpResponse httpResponse = RequestAllowAllFactory.makeRequest(httpReq);

            // Set data to response
            if (httpResponse != null) {
                resp.setRespBody(EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8));
                resp.setCode(httpResponse.getStatusLine().getStatusCode());
            }
        } catch (UnsupportedEncodingException e) {
            resp.setRespBody(e.getMessage());
            resp.setCode(ErrorCode.ERROR_UNKNOWN);
            e.printStackTrace();
        } catch (ParseException e) {
            resp.setRespBody(e.getMessage());
            resp.setCode(ErrorCode.ERROR_UNKNOWN);
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            resp.setRespBody(e.getMessage());
            resp.setCode(ErrorCode.ERROR_LOST_CONNECTION);
            e.printStackTrace();
        } catch (IOException e) {
            resp.setRespBody(e.getMessage());
            resp.setCode(ErrorCode.ERROR_LOST_CONNECTION);
            e.printStackTrace();
        }

        return resp;
    }
}
