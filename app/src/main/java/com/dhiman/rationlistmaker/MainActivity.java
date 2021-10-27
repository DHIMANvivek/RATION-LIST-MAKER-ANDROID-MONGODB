package com.dhiman.rationlistmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MainActivity extends AppCompatActivity {
    MongoClient mongoClient ;
    MongoDatabase mongoDatabase ;

    Button btn ;
    EditText data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


btn = findViewById(R.id.btn);

data = findViewById(R.id.data);




        Realm.init(this);
        String appID = "ration-zkbly";
        App app = new App(new AppConfiguration.Builder(appID)
                .build());
        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                User user = app.currentUser();


                // interact with realm using your user object here
            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = app.currentUser();
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Ration");
                MongoCollection<Document> mongoCollection = mongoClient.getDatabase("Ration").getCollection("Data");

               mongoCollection.insertOne(new Document("userid",user.getId()).append("data",data.getText().toString())).getAsync(result -> {



                   if(result.isSuccess()){

                       Log.v("data","inserted success");


                   }
                   else{
                       Log.v("data","insertion failed");
                   }

               });



            }
        });

    }
}