package service;

import dao.ResultDao;
import Model.Result;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultService {

    private final ResultDao resultDao;

    public ResultService(ResultDao resultDao) {
        this.resultDao = resultDao;
    }

    public void saveResultToDB(Result result) {
        resultDao.saveResultToDB(result);
    }

    public Result getBestPlayerFromDB() {
        return resultDao.getBestPlayerFromDB();
    }

    public List<Result> getResultsOfAllQuizzesOfUser(int userId) {
        return resultDao.getResultsOfAllQuizzesOfUser(userId);
    }

    public void openCSV(String path) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(path));
            }
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
}
