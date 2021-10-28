package com.defv.appretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import Interface.JsonPlaceHolderApi;
import Model.Posts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView jsonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonTest = findViewById(R.id.jsonText);

        getPosts();
    }

    private void getPosts(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Posts>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if(!response.isSuccessful()){

                    jsonTest.setText("Codigo: " + response.code());
                    return;
                }

                List<Posts> postsList = response.body();

                for(Posts post: postsList){

                    String contenido = "";
                    contenido += "userId: " + post.getUserId() + "\n";
                    contenido += "id: " + post.getId() + "\n";
                    contenido += "tittle: " + post.getTittle() + "\n";
                    contenido += "body: " + post.getBody() + "\n\n";
                    contenido += "==========================================\n\n";
                    jsonTest.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {

                jsonTest.setText(t.getMessage());

            }
        });
    }
}