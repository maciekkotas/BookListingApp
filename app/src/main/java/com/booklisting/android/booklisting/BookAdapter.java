package com.booklisting.android.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by macie on 20.06.2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleView.setText(currentBook.getTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorView.setText(currentBook.getAuthor());

        ImageView bookView = (ImageView) listItemView.findViewById(R.id.book_image_view);
        Picasso.with(getContext()).load(currentBook.getUrl()).into(bookView);

        return listItemView;

    }

}
