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
import java.util.HashMap;
import java.util.List;

public class PieChartPage {

    private MainCtrl mainCtrl;
    private ServerUtils serverUtils;

    ObservableList<PieChart.Data> pieChartData;

    HashMap<String, Double> map;
    private Event event;

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
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Iphone 5S", 13),
                new PieChart.Data("Samsung Grand", 25),
                new PieChart.Data("MOTO G", 10),
                new PieChart.Data("Nokia Lumia", 22));
        List<Expense> expenses=serverUtils.
                getExpensesByInvitationId(event.getInvitationID());
        for(Expense expense : expenses){
            String tag=expense.getType();
            if(map.containsKey(tag)){
                Double weight=map.get(tag);
                map.put(tag,weight+expense.getAmount());
            }else
                map.put(tag, expense.getAmount());
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
    }
}
