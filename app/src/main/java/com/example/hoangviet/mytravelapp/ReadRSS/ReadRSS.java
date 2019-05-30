//package com.example.hoangviet.mytravelapp.ReadRSS;
//
//import android.os.AsyncTask;
//
//import com.example.hoangviet.mytravelapp.Conect.Http;
//
//import java.io.IOException;
//
//public class ReadRSS extends AsyncTask<String, Void, String> {
//
//    Http http;
//    String RSSurl;
//    String resutl;
//    @Override
//    protected String doInBackground(String... strings) {
//        try {
//            resutl = http.read(RSSurl);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return resutl;
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//
//        //Toast.makeText(ExploreFragment.this,s.toString(),Toast.LENGTH_LONG).show();
//    }
//}
