package com.example.hun.baseballrecord.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hun.baseballrecord.R;

import java.util.ArrayList;


public class SearchRecordPopup {
    private Context context;

    public SearchRecordPopup(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.search_record_popup);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = dlg.findViewById(R.id.mesgase);
        final Button okButton =  dlg.findViewById(R.id.okButton);
        final Button cancelButton = dlg.findViewById(R.id.cancelButton);
        final Spinner yearSpinner = dlg.findViewById(R.id.search_year);
        final TextView yearSearchTxt = dlg.findViewById(R.id.search_year_txt);
        final Spinner positionSpinner = dlg.findViewById(R.id.search_position);
        final TextView positionSearchTxt = dlg.findViewById(R.id.search_position_txt);
        final Spinner teamSpinner = dlg.findViewById(R.id.search_team);
        final TextView teamSearchTxt = dlg.findViewById(R.id.search_team_txt);
        final Spinner seasonSpinner = dlg.findViewById(R.id.search_season);
        final TextView seasonSearchTxt = dlg.findViewById(R.id.search_season_txt);
        final Spinner battingSpinner = dlg.findViewById(R.id.search_batting);
        final TextView battingSearchTxt = dlg.findViewById(R.id.search_batting_txt);

       okButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
            // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
           // main_label.setText(message.getText().toString());
            Toast.makeText(context, "\"" +  message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

            // 커스텀 다이얼로그를 종료한다.
            dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                yearSearchTxt.setText(adapterView.getItemAtPosition(position) + "년");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                positionSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                teamSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                seasonSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        battingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                battingSearchTxt.setText(String.valueOf(adapterView.getItemAtPosition(position)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }
}
