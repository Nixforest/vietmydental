package vietmydental.immortal.com.vietmydental.api;
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

import vietmydental.immortal.com.vietmydental.utils.BaseModel;
import vietmydental.immortal.com.vietmydental.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.utils.ErrorCode;

/**
 * Created by nguyenpt on 4/16/18.
 */

public abstract class BaseRequest extends AsyncTask {
    /** Request type */
    private int requestType = 0;
    /** Response result */
    private BaseResponse response;
    /** Sub URL */
    private String subURL = DomainConst.BLANK;

    /**
     * Constructor.
     * @param subURL Sub URL
     */
    protected BaseRequest(String subURL) {
        setSubURL(subURL);
    }

    /**
     * Set sub URL value.
     * @param subURL Sub URL value
     */
    public void setSubURL(String subURL) {
        this.subURL = subURL;
    }

    /**
     * Get sub URL value.
     * @return Sub URL value
     */
    public String getSubURL() {
        return subURL;
    }

    /**
     * Set request type.
     * @param requestType Request type value
     */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /**
     * Base response class
     */
    public class BaseResponse {
        /** Status code */
        private int statusCode = 0;
        /** Response body */
        private String responseBody = DomainConst.BLANK;
        /** Data */
        private JSONObject data = null;
        /** JSON status */
        private int jsonStatus = 0;

        /**
         * Get error code.
         *
         * @return Error code value
         */
        public int getErrorCode() {
            initData();
            if ((data != null) && data.has(DomainConst.KEY_CODE)) {
                try {
                    return data.getInt(DomainConst.KEY_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return statusCode;
        }

        /**
         * Initialize data.
         */
        private void initData() {
            if ((data == null) && (responseBody != null)) {
                try {
                    data = new JSONObject(responseBody);
                } catch (JSONException e) {
                    data = new JSONObject();
                    try {
                        data.put(DomainConst.MENU_ID_LIST.MESSAGE, "");
                    } catch (JSONException e1) {
                    }
                    Log.e("Response", "Error when get url " + getServerURL() + getSubURL() + " and response: " + responseBody);
                }
            }
        }

        /**
         * Check is success.
         *
         * @return True if "jsonStatus" is 1, false otherwise
         */
        public boolean isSuccess() {
            initData();
            if (data != null) {
                try {
                    jsonStatus = data.getInt(DomainConst.KEY_STATUS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonStatus == 1;
        }

        /**
         * Get data.
         *
         * @return Data value
         */
        public JSONObject getData() {
            initData();
            return data;
        }

        /**
         * Get record.
         *
         * @return Record value
         */
        public JSONObject getRecord() {
            JSONObject data = getData();
            if (data != null) {
                try {
                    return data.getJSONObject(DomainConst.KEY_RECORD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
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
        } catch (Exception e) {
            Log.e(DomainConst.LOG_TAG_ERROR,
                    DomainConst.LOG_MSG_HTTP_ACCESS_FAILED, e);
        }

        Map<String, File> files = buildFileParameter();

        if (files != null && files.size() > 0) {
            Log.w(DomainConst.LOG_TAG_WARN,
                    DomainConst.LOG_MSG_GET_DATA_FROM_URL + BaseRequest.getServerURL());
//            response = makeGeneralRequest(BaseRequest.getServerURL() + getSubURL(), files, nameValuePairs);
        } else {
            Log.w(DomainConst.LOG_TAG_WARN,
                    DomainConst.LOG_MSG_GET_DATA_FROM_URL + BaseRequest.getServerURL());
            response = makeGeneralRequest(BaseRequest.getServerURL() + getSubURL(), requestType, entity);
        }

        // remove optimizise images after request finish if possible
//        removeOptimizeImage();

        return null;
    }

    /**
     * Get response from post request.
     *
     * @param url    String Link to connect
     * @param entity body request
     * @return BaseResponse always return object. Object is empty if request failed.
     */
    protected BaseResponse makeGeneralRequest(String url, int requestType, HttpEntity entity) {
        Log.w(DomainConst.LOG_TAG_WARN,
                DomainConst.LOG_MSG_GET_DATA_FROM_URL + url);
        BaseResponse responseData = new BaseResponse();
        HttpUriRequest httpRequest;
        try {
            if (requestType == DomainConst.REQUEST_POST) {
                // Make post request
                HttpPost httpPost = new HttpPost(url);

                // Add your data & get response
                httpPost.setEntity(entity);

                // Set to request
                httpRequest = httpPost;
                httpRequest.setHeader(DomainConst.HTTP_REQ_HEADER,
                        DomainConst.HTTP_REQ_HEADER_CONTENT);
            } else if (requestType == DomainConst.REQUEST_GET) {
                // Make get request
                // Set to request
                httpRequest = new HttpGet(url);
            } else if (requestType == DomainConst.REQUEST_PUT) {
                // Make put request
                HttpPut httpPut = new HttpPut(url);

                // Add your data & get response
                httpPut.setEntity(entity);

                // Set to request
                httpRequest = httpPut;
            } else if (requestType == DomainConst.REQUEST_DELETE) {
                // Make get request
                // Set to request
                httpRequest = new HttpDelete(url);
            } else {
                // requestType is invalid
                return responseData;
            }

            HttpResponse response = RequestAllowAllFactory.makeRequest(httpRequest);

            // Set data to response
            if (response != null) {
                responseData.responseBody = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                responseData.statusCode = response.getStatusLine().getStatusCode();
            }
        } catch (UnsupportedEncodingException e) {
            responseData.statusCode = ErrorCode.ERROR_UNKNOWN;
            responseData.responseBody = e.getMessage();
            e.printStackTrace();
        } catch (ParseException e) {
            responseData.statusCode = ErrorCode.ERROR_UNKNOWN;
            responseData.responseBody = e.getMessage();
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            responseData.statusCode = ErrorCode.ERROR_LOST_CONNECTION;
            responseData.responseBody = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            responseData.statusCode = ErrorCode.ERROR_LOST_CONNECTION;
            responseData.responseBody = e.getMessage();
            e.printStackTrace();
        }
        return responseData;
    }

//    /**
//     * Get response from post request.
//     *
//     * @param url            String Link to connect
//     * @param files          List of files
//     * @param nameValuePairs List of key-value
//     * @return BaseResponse always return object. Object is empty if request failed.
//     */
//    protected BaseResponse makeGeneralRequest(String url, Map<String, File> files, List<NameValuePair> nameValuePairs) {
//        Log.w(DomainConst.LOG_TAG_WARN,
//                DomainConst.LOG_MSG_GET_DATA_FROM_URL + url);
//        BaseResponse responseData = new BaseResponse();
//        try {
//
//            HttpPost httppost = new HttpPost(url);
//            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName(HTTP.UTF_8));
//
//            // Set value field
//            for (NameValuePair name : nameValuePairs) {
//                multipartEntity.addPart(name.getName(), new StringBody(name.getValue(), Charset.forName("UTF-8")));
//            }
//
//            // Set file name
//            if (files != null) {
//                for (Map.Entry<String, File> data : files.entrySet()) {
//                    multipartEntity.addPart(data.getKey(), new FileBody(data.getValue()));
//                }
//            }
//
//            // Set data to post header
//            httppost.setEntity(multipartEntity);
//
//            HttpResponse response = RequestAllowAllFactory.makeRequest(httppost);
//
//            // Set data to response
//            if (response != null) {
//                responseData.responseBody = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
//                responseData.statusCode = response.getStatusLine().getStatusCode();
//            }
//
//        } catch (UnsupportedEncodingException e) {
//            responseData.statusCode = ErrorCode.ERROR_UNKNOWN;
//            responseData.responseBody = e.getMessage();
//            e.printStackTrace();
//        } catch (ParseException e) {
//            responseData.statusCode = ErrorCode.ERROR_UNKNOWN;
//            responseData.responseBody = e.getMessage();
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            responseData.statusCode = ErrorCode.ERROR_LOST_CONNECTION;
//            responseData.responseBody = e.getMessage();
//            e.printStackTrace();
//        } catch (IOException e) {
//            responseData.statusCode = ErrorCode.ERROR_LOST_CONNECTION;
//            responseData.responseBody = e.getMessage();
//            e.printStackTrace();
//        }
//
//        return responseData;
//    }

//    /**
//     * Convert to low quality image into internal storage. It will be removed after request
//     *
//     * @param context
//     * @param listImage
//     * @param witdh
//     * @param height
//     * @return
//     */
//    public ArrayList<File> requestOptimizeImage(Context context, List<File> listImage, int witdh, int height) {
//        ArrayList<File> listImageNew = new ArrayList<File>();
//        for (int i = 0; i < listImage.size(); i++) {
//            File originalFile = listImage.get(i);
//            File tempFile = new File(context.getFilesDir(), "temp_" + originalFile.getName());
//            try {
//                copyFile(originalFile, tempFile);
//            } catch (Exception e) {
//            }
//            File outputFile = new File(context.getFilesDir(), originalFile.getName());
//            if (tempFile.exists()) {
//
//                if (tempFile.length() > DomainConst.MAX_UPLOAD_FILE_SIZE) {
//                    convertImage(originalFile, outputFile, witdh, height);
//                    listImageNew.add(outputFile);
//                    try {
//                        tempFile.delete();
//                    } catch (Exception ex) {
//                    }
//                } else {
//                    listImageNew.add(tempFile);
//                }
//            }
//
//        }
//        bufferConvertImageList.addAll(listImageNew);
//        return listImageNew;
//    }
//
//    private void removeOptimizeImage() {
//        if (bufferConvertImageList != null) {
//            for (File file : bufferConvertImageList) {
//                try {
//                    if (file != null && file.exists()) {
//                        file.delete();
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void copyFile(File src, File dst) throws IOException {
//
//        FileChannel inChannel = null;
//        FileChannel outChannel = null;
//
//        try {
//            inChannel = new FileInputStream(src).getChannel();
//            outChannel = new FileOutputStream(dst).getChannel();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            inChannel.transferTo(0, inChannel.size(), outChannel);
//        } finally {
//            if (inChannel != null)
//                inChannel.close();
//            if (outChannel != null)
//                outChannel.close();
//        }
//    }
//
//    public void convertImage(File pathOfInputImage, File pathOfOutputImage, int dstWidth, int dstHeight) {
//        try {
//            int inWidth = 0;
//            int inHeight = 0;
//
//            InputStream in = new FileInputStream(pathOfInputImage);
//
//            // decode image size (decode metadata only, not the whole image)
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(in, null, options);
//            in.close();
//            in = null;
//
//            // save width and height
//            inWidth = options.outWidth;
//            inHeight = options.outHeight;
//
//            // decode full image pre-resized
//            in = new FileInputStream(pathOfInputImage);
//            options = new BitmapFactory.Options();
//            // calc rought re-size (this is no exact resize)
//            options.inSampleSize = Math.max(inWidth / dstWidth, inHeight / dstHeight);
//            // decode full image
//            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);
//
//            // calc exact destination size
//            Matrix m = new Matrix();
//            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
//            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
//            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
//            float[] values = new float[9];
//            m.getValues(values);
//
//            // resize bitmap
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
//
//            // save image
//            try {
//                FileOutputStream out = new FileOutputStream(pathOfOutputImage);
//                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            } catch (Exception e) {
//                Log.e("Image", e.getMessage(), e);
//            }
//
//            resizedBitmap.recycle();
//        } catch (IOException e) {
//            Log.e("Image", e.getMessage(), e);
//        }
//    }
}
