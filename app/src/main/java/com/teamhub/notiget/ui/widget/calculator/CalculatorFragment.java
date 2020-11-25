package com.teamhub.notiget.ui.widget.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.teamhub.notiget.R;
import com.teamhub.notiget.ui.widget.base.BaseFragment;


public class CalculatorFragment extends BaseFragment {

    private CalculatorViewModel viewModel;
    private View root;
    private TextView math_text;
    private TextView result_text;
    private boolean isoperator = false ,Firstinput = true;
    double resultnum = 0, inputnum = 0;
    String operator = "＝", lastoperator = "＋";
    private Button btn_back_space, btn_percent, btn_clear, btn_number_7,
            btn_number_8, btn_number_9, btn_division, btn_number_4, btn_number_5,
            btn_number_6,
            btn_multiplication, btn_number_1, btn_number_2, btn_number_3, btn_plus,
            btn_number_0, btn_point, btn_equal, btn_minus;

    public static CalculatorFragment newInstance(View v) {
        return new CalculatorFragment(v);
    }

    public CalculatorFragment(View v) {
        super(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        root = inflater.inflate(R.layout.widget_calculator, container, false);
        // TODO: init reference
        initReferences();
        initEvents();

        return root;
    }
    private void initReferences(){
        result_text = (TextView) root.findViewById(R.id.result_text);
        math_text = (TextView) root.findViewById(R.id.math_text);
        btn_number_0 = (Button) root.findViewById(R.id.btn_number_0);
        btn_number_1 = (Button) root.findViewById(R.id.btn_number_1);
        btn_number_2 = (Button) root.findViewById(R.id.btn_number_2);
        btn_number_3 = (Button) root.findViewById(R.id.btn_number_3);
        btn_number_4 = (Button) root.findViewById(R.id.btn_number_4);
        btn_number_5 = (Button) root.findViewById(R.id.btn_number_5);
        btn_number_6 = (Button) root.findViewById(R.id.btn_number_6);
        btn_number_7 = (Button) root.findViewById(R.id.btn_number_7);
        btn_number_8 = (Button) root.findViewById(R.id.btn_number_8);
        btn_number_9 = (Button) root.findViewById(R.id.btn_number_9);
        btn_back_space = (Button) root.findViewById(R.id.btn_back_space);
        btn_clear = (Button) root.findViewById(R.id.btn_clear);
        btn_multiplication = (Button) root.findViewById(R.id.btn_multiplication);
        btn_plus = (Button) root.findViewById(R.id.btn_plus);
        btn_division = (Button) root.findViewById(R.id.btn_division);
        btn_point = (Button) root.findViewById(R.id.btn_point);
        btn_equal = (Button) root.findViewById(R.id.btn_equal);
        btn_minus = (Button) root.findViewById(R.id.btn_minus);

    }

    private void initEvents() {
        btn_number_0.setOnClickListener(clickListener);
        btn_number_1.setOnClickListener(clickListener);
        btn_number_2.setOnClickListener(clickListener);
        btn_number_3.setOnClickListener(clickListener);
        btn_number_4.setOnClickListener(clickListener);
        btn_number_5.setOnClickListener(clickListener);
        btn_number_6.setOnClickListener(clickListener);
        btn_number_7.setOnClickListener(clickListener);
        btn_number_8.setOnClickListener(clickListener);
        btn_number_9.setOnClickListener(clickListener);
        btn_clear.setOnClickListener(clearClick);
        btn_back_space.setOnClickListener(backspaceClick);
        btn_multiplication.setOnClickListener(operatorClick);
        btn_plus.setOnClickListener(operatorClick);
        btn_division.setOnClickListener(operatorClick);
        btn_point.setOnClickListener(pointClick);
        btn_equal.setOnClickListener(equlaClick);
        btn_minus.setOnClickListener(operatorClick);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Firstinput) {
                result_text.setText(view.getTag().toString());
                Firstinput = false;
                if(operator.equals("＝")){
                    math_text.setText("");
                    isoperator = false;
                }
            } else {
                if (result_text.getText().toString().equals("0")) {
                    Firstinput = true;
                } else {
                    result_text.append(view.getTag().toString());
                }
            }
        }
    };

    View.OnClickListener pointClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Firstinput) {
                result_text.setText("0" + view.getTag().toString());
                Firstinput = false;
            } else {
                if (result_text.getText().toString().contains(".")) {
                } else {
                    result_text.append(view.getTag().toString());
                }
            }
        }
    };

    View.OnClickListener operatorClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isoperator = true;
            lastoperator = view.getTag().toString();
            if(Firstinput){
                if(operator.equals("＝")){
                    operator = view.getTag().toString();
                    resultnum = Double.parseDouble(result_text.getText().toString());
                    math_text.setText(resultnum + "" + operator + "");
                }else {
                    operator = view.getTag().toString();
                    String get_math_text = math_text.getText().toString();
                    String subString = get_math_text.substring(0, get_math_text.length()
                            - 2);
                    math_text.setText(subString);
                    math_text.append(operator + "");
                }

            }else {
                inputnum = Double.parseDouble(result_text.getText().toString());
                resultnum = calculator(resultnum, inputnum, operator);

                result_text.setText(String.valueOf(resultnum));
                Firstinput = true;
                operator = view.getTag().toString();
                math_text.append(inputnum + " " + operator + " ");
            }
        }
    };

    View.OnClickListener equlaClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(Firstinput){
                if(isoperator) {
                    math_text.setText(resultnum + "" + lastoperator + "" + inputnum + "＝");
                            resultnum = calculator(resultnum, inputnum, lastoperator);
                    result_text.setText(String.valueOf(resultnum));
                }
            }else {
                inputnum = Double.parseDouble(result_text.getText().toString());
                resultnum = calculator(resultnum, inputnum, operator);
                result_text.setText(String.valueOf(resultnum));
                Firstinput = true;
                operator = view.getTag().toString();
                math_text.append(inputnum + " " + operator + " ");
            }
        }
    };

    View.OnClickListener clearClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            result_text.setText("0");
            math_text.setText(" ");
            resultnum = 0;
            operator = "＝";
            Firstinput = true;
            isoperator = false;
        }
    };
    View.OnClickListener backspaceClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!Firstinput){
                String get_result_text = result_text.getText().toString();
                if(get_result_text.length() > 1) {
                    String subString = get_result_text.substring(0, get_result_text.length() - 1);
                    result_text.setText(subString);
                }else {
                    result_text.setText("0");
                    Firstinput = true;
                }
            }
        }
    };

    private double calculator(double resultnum, double inputnum, String operator) {
        switch (operator){
            case "＝":
                resultnum = inputnum;
                break;
            case "＋":
                resultnum = resultnum + inputnum;
                break;
            case "－":
                resultnum = resultnum - inputnum;
                break;
            case "×":
                resultnum = resultnum * inputnum;
                break;
            case "÷":
                resultnum = resultnum / inputnum;
                break;
        }
        return resultnum;
    }
}