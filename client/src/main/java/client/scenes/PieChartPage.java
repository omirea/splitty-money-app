package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PieChartPage {

    private MainCtrl mainCtrl;
    private ServerUtils serverUtils;

    ObservableList<PieChart.Data> pieChartData;

    HashMap<String, Double> map;
    private Event event;

    private List<Expense> expenses=new ArrayList<>();

    private List<String> tags=new ArrayList<>();

    @FXML
    private PieChart pieChartGoofy;



    @Inject
    public PieChartPage(MainCtrl mainCtrl, ServerUtils serverUtils){
        this.mainCtrl=mainCtrl;
        this.serverUtils=serverUtils;
        pieChartData = FXCollections.observableArrayList();
        map=new HashMap<>();
    }

    @FXML
    public void initialize(){
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//                new PieChart.Data("Iphone 5S", 13),
//                new PieChart.Data("Samsung Grand", 25),
//                new PieChart.Data("MOTO G", 10),
//                new PieChart.Data("Nokia Lumia", 22));
//
//




        pieChartGoofy.setData(pieChartData);

        //Setting the title of the Pie chart
        pieChartGoofy.setTitle("Event statistics");

        //setting the direction to arrange the data
        pieChartGoofy.setClockwise(true);

        //Setting the length of the label line
        pieChartGoofy.setLabelLineLength(50);

        //Setting the labels of the pie chart visible
        pieChartGoofy.setLabelsVisible(true);

        //Setting the start angle of the pie chart
        pieChartGoofy.setStartAngle(180);

    }

    public void createPieChart(){

//        for(Expense expense : expenses){
//            String tag=expense.getType();
//            Double weight=map.get(tag);
//            map.put(tag,weight+expense.getAmount());
//        }

        for(String s : tags) {
            System.out.println("Tag in pieChartAdd" + s);
            PieChart.Data pd = new PieChart.Data(s, map.get(s));
            // pd.getNode().setStyle("-fx-background-color: pink");
            pieChartData.add(pd);
            //pieChartData.add(new PieChart.Data("food", 20));
        }



        pieChartGoofy.setData(pieChartData);

        //Setting the title of the Pie chart
        pieChartGoofy.setTitle("Event statistics");

        //setting the direction to arrange the data
        pieChartGoofy.setClockwise(true);

        //Setting the length of the label line
        pieChartGoofy.setLabelLineLength(50);


        //Setting the labels of the pie chart visible
        pieChartGoofy.setLabelsVisible(true);

        //Setting the start angle of the pie chart
        pieChartGoofy.setStartAngle(180);
    }


    /**
     * set id of the event
     */
    public void setEvent(String id) {
        event = serverUtils.getEventByInvitationId(id);
        tags=event.getTags();
        for(String tag:tags) {
            map.put(tag, 20.00);
            System.out.println("tag in setEvent: " + tag);
        }
        expenses=serverUtils.getAllExpenses();
        createPieChart();
    }
}
