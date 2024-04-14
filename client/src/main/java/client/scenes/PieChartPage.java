package client.scenes;

import client.utils.ServerUtils;
import com.sun.javafx.charts.Legend;
import commons.Event;
import commons.Expense;
import commons.TagsClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PieChartPage {

    private MainCtrl mainCtrl;
    private ServerUtils serverUtils;

    ObservableList<PieChart.Data> pieChartData;

    HashMap<String, Double> map;
    private Event event;

    private Event eventWithTheTags;

    private List<Expense> expenses=new ArrayList<>();

    private List<TagsClass> tags=new ArrayList<>();

    private List<String> tagsInPieChart=new ArrayList<>();

    private List<TagsClass> allTags=new ArrayList<>();

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

        pieChartGoofy.getData().clear();
        pieChartGoofy.setData(pieChartData);
        pieChartGoofy.applyCss();
        pieChartGoofy.layout();

        //Setting the title of the Pie chart
        pieChartGoofy.setTitle("Event statistics");

        //setting the direction to arrange the data
        pieChartGoofy.setClockwise(true);

        //Setting the length of the label line
        pieChartGoofy.setLabelLineLength(100);

        //Setting the labels of the pie chart visible
        pieChartGoofy.setLabelsVisible(true);

        //Setting the start angle of the pie chart
        pieChartGoofy.setStartAngle(180);

    }

    public void createPieChart(){
        pieChartData.clear();
        pieChartGoofy.getData().clear();
        map.clear();

        for (TagsClass tag : tags) {
            map.put(tag.getName(), 0.00);
            System.out.println("tag in setEvent: " + tag.getName());
        }

        tags=eventWithTheTags.getTags();

        for(Expense expense : expenses){
            String tag=expense.getType();
            if(tag!=null && map.containsKey(tag)) {
                Double weight = map.get(tag);
                map.put(tag, weight + expense.getAmount());
            }
        }

        for(TagsClass s : tags) {
            tagsInPieChart.add(s.getName());
            System.out.println("Tag in pieChartAdd" + s.getName());
            PieChart.Data pd = new PieChart.Data(s.getName(), map.get(s.getName()));
            pieChartData.add(pd);
        }
        for(PieChart.Data pd : pieChartData){
            for(TagsClass tg : tags){
                if(tg.getName().equals(pd.getName()))
                    pd.getNode().setStyle("-fx-background-color: #" + tg.getHexCode());
            }
        }

        pieChartGoofy.setData(pieChartData);
        pieChartGoofy.applyCss();
        pieChartGoofy.layout();
        Set<Node> items = pieChartGoofy.lookupAll("Label.chart-legend-item");
        for(Node node : items){
            Label label=(Label) node;
            for(TagsClass tg : tags){
                System.out.println(tg.getName() + tg.getHexCode());
                if(tg.getName().equals(label.getText()))
                    label.getGraphic().setStyle("-fx-background-color: #" + tg.getHexCode());
            }
        }
    }


    /**
     * set id of the event
     */
    public void setEvent(String id) {
        event = serverUtils.getEventByInvitationId(id);
        expenses=serverUtils.getExpensesByInvitationId(event.getInvitationID());
        createPieChart();
    }

    public void setEventWithTags(Event eventWithTheTags) {
        this.eventWithTheTags = eventWithTheTags;
        tags=eventWithTheTags.getTags();
    }


    public void goBackToEvent(){
        mainCtrl.showEventOverview(event.getInvitationID());
    }
}