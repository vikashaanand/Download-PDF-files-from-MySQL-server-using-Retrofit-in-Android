package com.example.downloadingfilesfrommysqlserversuingretrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("downloadDocument.php")
    Call<DocumentPOJO> downloadDocs(
            @Field("SN") int sn
    );

}
