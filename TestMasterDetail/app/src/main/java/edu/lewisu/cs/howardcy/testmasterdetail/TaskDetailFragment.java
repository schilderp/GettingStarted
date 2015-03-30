package edu.lewisu.cs.howardcy.testmasterdetail;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A fragment representing a single Task detail screen.
 * This fragment is either contained in a {@link TaskListActivity}
 * in two-pane mode (on tablets) or a {@link TaskDetailActivity}
 * on handsets.
 */
public class TaskDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    EditText descEditText;
    Spinner prioritySpinner;
    CheckBox doneCheckBox;
    Button confirmButton;
    Uri taskUri;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Long id = getArguments().getLong(ARG_ITEM_ID);
            taskUri = Uri.parse(TaskProvider.CONTENT_URI + "/" + id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);
        descEditText = (EditText)rootView.findViewById(R.id.desc_edit_text);
        prioritySpinner = (Spinner) rootView.findViewById(R.id.spinner);
        doneCheckBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        confirmButton = (Button)rootView.findViewById(R.id.button);
        confirmButton.setOnClickListener(new AddTask());

        String buttonText;
        if(taskUri != null){
            buttonText = getResources().getString(R.string.update);
        }else{
            buttonText = getResources().getString(R.string.add);
        }
        confirmButton.setText(buttonText);


        if (taskUri != null){
            String[] projection = {TaskTable.COL_DESCRIPTION, TaskTable.COL_PRIORITY, TaskTable.COL_DONE};
            Cursor cursor = getActivity().getContentResolver().query(taskUri, projection,null, null, null );

            if (cursor != null ){
                cursor.moveToFirst();
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(TaskTable.COL_DESCRIPTION));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskTable.COL_PRIORITY));
                int check = cursor.getInt(cursor.getColumnIndexOrThrow(TaskTable.COL_DONE));

                descEditText.setText(desc);

                String[] priorities = getActivity().getResources().getStringArray(R.array.priorities);
                for(int i=0; i< priorities.length; i++){
                    if(priorities[i].equals(priority)){
                        prioritySpinner.setSelection(i);
                    }
                }

                if(check==1){
                    doneCheckBox.setChecked(true);
                }
            }
        }
        return rootView;
    }

    private class AddTask implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String desc = descEditText.getText().toString();
            String priority = prioritySpinner.getSelectedItem().toString();
            int done = 0;
            if (doneCheckBox.isChecked()){
                done =1 ;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(TaskTable.COL_DESCRIPTION, desc);
            contentValues.put(TaskTable.COL_PRIORITY, priority);
            contentValues.put(TaskTable.COL_DONE, done);

            if(taskUri == null && !desc.equals("")){
                taskUri = getActivity().getContentResolver().insert(taskUri, contentValues);
            }else if(taskUri!=null){
                getActivity().getContentResolver().update(taskUri, contentValues, null, null);
            }

        }
    }
}
