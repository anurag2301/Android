package com.anurag2301.quiz;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Created by anura on 9/11/2017.
 */

public class Questions {


    public ArrayList<Question> questionList = new ArrayList<Question>();
    public HashMap<Integer, Integer> recordAns = new HashMap<Integer, Integer>();
    public HashMap<String, Questions> list = new HashMap<String, Questions>();

    public int onCategory;
    public String[] categoryList;
    public int[] onQuestion;
    public int[] score;
    public int qAnswered;
    public int totalQuestions;

    public void init() {
        totalQuestions = 0;
        qAnswered = 0;
        String[] questionListCat1 = { "What is 2*2?", "What is 5*50?", "What is 5!?", "What is 6*8?", "What is 4-4?" };
        String[] ansListCat1 = { "4,2,8,10", "250,150,200,70", "120,160,40,720", "48,52,33,61", "0,1,-1,2" };

        String[] questionListCat2 = { "Where is Paris?", "What's the capital of England?", "Where is Miami?",
                "What's capital of UAE?", "Where is Luxembourg?" };
        String[] ansListCat2 = { "France,England,London,Italy", "London,New York,Paris,Hong Kong",
                "Florida,California,Ohio,Texas", "Abu Dhabi,Dubai,Omean,Jerusalem", "Europe,Australia,USA,Asia" };

        addQuestions("Maths", questionListCat1, ansListCat1);
        addQuestions("Geography", questionListCat2, ansListCat2);
    }

    public void addQuestions(String category, String[] questionListInCat, String[] ansListInCat) {

        ArrayList<String> qInCat = new ArrayList<String>();
        ArrayList<String> ansInCat = new ArrayList<String>();
        for (int i = 0; i < questionListInCat.length; i++) {
            qInCat.add(questionListInCat[i]);
            ansInCat.add(ansListInCat[i]);
        }

        totalQuestions += qInCat.size();
        while (qInCat.size() > 0) {
            Random rand = new Random();
            int random = (int) (rand.nextInt(qInCat.size()));

            Question q = new Question();
            q.question = qInCat.get(random);
            String[] ansList = ansInCat.get(random).split(",");
            q.correctAnswer = ansList[0];

            qInCat.remove(random);
            ansInCat.remove(random);
            System.out.println("3" + qInCat.size());

            ArrayList<String> ans = new ArrayList<String>();
            for (int i = 0; i < ansList.length; i++) {
                ans.add(ansList[i]);
            }
            ArrayList<String> arrangedAns = new ArrayList<String>();

            while (ans.size() > 0) {
                int random2 = rand.nextInt(ans.size());
                arrangedAns.add(ans.get(random2));
                ans.remove(random2);
            }

            q.options = arrangedAns;

            if(list.containsKey(category)) {
                Questions que = list.get(category);
                que.questionList.add(q);
            } else {
                Questions que = new Questions();
                que.questionList.add(q);
                list.put(category, que);
            }

        }

        onCategory = 0;
        Set<String> keys = list.keySet();
        onQuestion = new int[keys.size()];
        categoryList = new String[keys.size()];
        score = new int[keys.size()];



        int i = 0;
        for(String key: keys) {
            onQuestion[i] = 0;
            score[i] = 0;
            categoryList[i] = key;
            i++;
        }

    }

    public Questions() {
    }

    public boolean addScore(int ansNo) {
        qAnswered++;
        recordAns.put(onCategory*100 + onQuestion[onCategory], ansNo);
        Question q = list.get(categoryList[onCategory]).questionList.get(onQuestion[onCategory]);
        if(q.correctAnswer.equals(q.options.get(ansNo))) {
            score[onCategory] += 2;
            return true;
        } else if(score[onCategory] > 0) {
            score[onCategory] -= 1;
            return false;
        } else {
            return false;
        }
    }

    public int getScore() {
        return score[onCategory];
    }

    public Question getNewQuestion() {
        ArrayList<Question> ql  = list.get(categoryList[onCategory]).questionList;
        onQuestion[onCategory] = (onQuestion[onCategory] + 1)%ql.size();
        return ql.get(onQuestion[onCategory]);
    }

    public Question getQuestionChangeCategory(int categoryNo) {
        onCategory = categoryNo;
        ArrayList<Question> ql  = list.get(categoryList[onCategory]).questionList;
        return ql.get(onQuestion[onCategory]);
    }


    public static void main(String[] args) {
        Questions q = new Questions();
    }

    public void nextQuestion(){
        onQuestion[onCategory] +=1;
        onQuestion[onCategory] %=list.get(categoryList[onCategory]).questionList.size();
    }

    public void prevQuestion(){
        onQuestion[onCategory] -=1;
        if(onQuestion[onCategory] <0) {
            onQuestion[onCategory] = 0;
        }
    }
}