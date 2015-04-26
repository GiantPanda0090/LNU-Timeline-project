/**
 * Created by ajace_000 on 4/24/2015.
 */
/*
*1DV008 PROJECT IN COMPUTER SCIENCE
*TIMELINE PROJECT
*MITIME
*GROUP MEMBER JOHN JOHAN AUSTIN WASAN LI
*VERSION CONTROL GITHUB
* SOME CLASS GOT IT OWN OWNER AND CREATER
*/





    import javafx.beans.value.ChangeListener;
    import javafx.beans.value.ObservableValue;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.scene.control.ListView;
    import javafx.scene.control.TextField;




    public class SearchTimeline {

        ObservableList<String> entries = FXCollections.observableArrayList();
        ListView list = new ListView();

        public void search(TextField txt) {




            txt.textProperty().addListener(
                    new ChangeListener() {
                        public void changed(ObservableValue observable,
                                            Object oldVal, Object newVal) {
                            handleSearchByKey2((String)oldVal, (String)newVal);
                        }
                    });


        }



        public void handleSearchByKey2(String oldVal, String newVal) {
            // If the number of characters in the text box is less than last time
            // it must be because the user pressed delete
            if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
                // Restore the lists original set of entries
                // and start from the beginning
                list.setItems( entries );
            }

            // Break out all of the parts of the search text
            // by splitting on white space
            String[] parts = newVal.toUpperCase().split(" ");

            // Filter out the entries that don't contain the entered text
            ObservableList<String> subentries = FXCollections.observableArrayList();
            for ( Object entry: list.getItems() ) {
                boolean match = true;
                String entryText = (String)entry;
                for ( String part: parts ) {
                    // The entry needs to contain all portions of the
                    // search string *but* in any order
                    if ( ! entryText.toUpperCase().contains(part) ) {
                        match = false;
                        break;
                    }
                }

                if ( match ) {
                    subentries.add(entryText);
                }
            }
            list.setItems(subentries);
        }
    }


