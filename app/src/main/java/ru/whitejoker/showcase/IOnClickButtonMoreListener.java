package ru.whitejoker.showcase;

import android.view.View;

//интерфейс для обработки клика по элементу списка в адаптере
public interface IOnClickButtonMoreListener {
    void onClickButtonCallback(View view, int position);
}
