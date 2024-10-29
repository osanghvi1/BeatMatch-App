package com.example.androidexample;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Custom request class for handling multipart/form-data requests with Volley.
 *
 * This class extends the Volley Request class, specifically handling multipart requests.
 * It allows sending both binary (like images) and text data together in one request.
 **/
public class MultipartRequest extends Request<String> {

    private final Response.Listener<String> mListener;
    private final Response.ErrorListener mErrorListener;
    private final byte[] mImageData;  // Optional: image data
    private final Map<String, String> mParams; // Additional text parameters
    private final String mBoundary = "apiclient-" + System.currentTimeMillis();
    private final String mLineEnd = "\r\n";
    private final String mTwoHyphens = "--";

    public MultipartRequest(int method, String url, byte[] imageData, Map<String, String> params,
                            Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mImageData = imageData; // Optional: Set to null if no image
        this.mParams = params;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + mBoundary;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // Write additional parameters
            if (mParams != null && !mParams.isEmpty()) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    dos.writeBytes(mTwoHyphens + mBoundary + mLineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + mLineEnd);
                    dos.writeBytes(mLineEnd);
                    dos.writeBytes(entry.getValue());
                    dos.writeBytes(mLineEnd);
                }
            }

            // Write image data if available
            if (mImageData != null) {
                dos.writeBytes(mTwoHyphens + mBoundary + mLineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"image.jpg\"" + mLineEnd);
                dos.writeBytes("Content-Type: image/jpeg" + mLineEnd);
                dos.writeBytes(mLineEnd);
                dos.write(mImageData);
                dos.writeBytes(mLineEnd);
            }

            // End of multipart form data
            dos.writeBytes(mTwoHyphens + mBoundary + mTwoHyphens + mLineEnd);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = new String(response.data, StandardCharsets.UTF_8);
            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }
}
