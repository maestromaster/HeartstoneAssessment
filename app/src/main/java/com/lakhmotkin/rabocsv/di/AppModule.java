package com.lakhmotkin.rabocsv.di;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.lakhmotkin.rabocsv.App;
import com.lakhmotkin.rabocsv.C;
import com.lakhmotkin.rabocsv.repository.data.CSVHelper;
import com.lakhmotkin.rabocsv.repository.data.CSVHelperType;
import com.lakhmotkin.rabocsv.repository.data.CardsRepository;
import com.lakhmotkin.rabocsv.repository.data.CardsRepositoryType;
import com.lakhmotkin.rabocsv.repository.data.db.AppDatabase;
import com.lakhmotkin.rabocsv.repository.data.db.AppDbHelper;
import com.lakhmotkin.rabocsv.repository.data.db.DbHelper;
import com.lakhmotkin.rabocsv.viewmodel.CardsListViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, C.DB_NAME).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Singleton
    @Provides
    CardsRepositoryType provideCardsRepository(DbHelper dbHelper, CSVHelperType csvHelper) {
        return new CardsRepository(dbHelper, csvHelper);
    }

    @Singleton
    @Provides
    CSVHelperType provideCSVHelper(Context context) {
        return new CSVHelper(context);
    }

    @Singleton
    @Provides
    CardsListViewModelFactory provideCardsListViewModelFactory(CardsRepositoryType repository) {
        return new CardsListViewModelFactory(repository);
    }
}
