package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class News_RecyclerAdapter extends RecyclerView.Adapter<News_RecyclerAdapter.myViewHolder> {

    private final NewsInterface newsInterface;

    Context context;
    ArrayList<NewsModel> newsModels;

    public News_RecyclerAdapter(Context context, ArrayList<NewsModel> newsModels, NewsInterface newsInterface){

        this.context = context;
        this.newsModels = newsModels;
        this.newsInterface = newsInterface;

    }

    @NonNull
    @Override
    public News_RecyclerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new News_RecyclerAdapter.myViewHolder(view,newsInterface);
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.txtTitle.setText(newsModels.get(position).getTitle());
//        holder.txtAuthor.setText("- " + newsModels.get(position).getAuthor());
//        holder.chkSaved.setChecked(newsModels.get(position).isSelected());
//
//        Glide.with(context).load(newsModels.get(position).getImage()).into(holder.image);
//
//    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        holder.txtTitle.setText(newsModels.get(position).getTitle());
        holder.txtAuthor.setText("- " + newsModels.get(position).getSource().toUpperCase());
//        holder.chkSaved.setChecked(newsModels.get(position).isSelected());

        Glide.with(context).load(newsModels.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }


//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        CheckBox chkSaved;
//        ImageView image;
//        TextView txtTitle,txtAuthor;
//        LinearLayout rowItem;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            image = itemView.findViewById(R.id.img_article);
//            txtTitle = itemView.findViewById(R.id.txt_title);
//            txtAuthor = itemView.findViewById(R.id.txt_link);
//            rowItem = itemView.findViewById(R.id.rowItem);
//
//
//            chkSaved = itemView.findViewById(R.id.chkSaved);
//
//            chkSaved.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    boolean isChecked = ((CheckBox) view).isChecked();
//                    int pos = getAdapterPosition();
//
//                    if (isChecked){
//                        newsModels.get(getAdapterPosition()).setSelected(true);
//                        Toast.makeText(context.getApplicationContext(), newsModels.get(pos).getTitle().toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context.getApplicationContext(), "" + newsModels.get(pos).isSelected(), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        newsModels.get(getAdapterPosition()).setSelected(false);
//                        Toast.makeText(context.getApplicationContext(), newsModels.get(pos).getTitle().toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context.getApplicationContext(), "" + newsModels.get(pos).isSelected(), Toast.LENGTH_SHORT).show();
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//
//            rowItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    int position = getAdapterPosition();
//
//                    Intent intent = new Intent(context.getApplicationContext(),ArticleViewActivity.class);
//
//                    intent.putExtra("title",newsModels.get(position).getTitle());
//                    intent.putExtra("author",newsModels.get(position).getAuthor());
//                    intent.putExtra("image",newsModels.get(position).getImage());
//                    intent.putExtra("description",newsModels.get(position).getDescription());
//                    intent.putExtra("published_at",newsModels.get(position).getPublishDate());
//                    intent.putExtra("source",newsModels.get(position).getSource());
//                    intent.putExtra("category",newsModels.get(position).getCategory());
//
//                    context.startActivity(intent);
//                }
//            });
//        }
//    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView txtTitle,txtAuthor;
//        CheckBox chkSaved;

        public myViewHolder(@NonNull View itemView, NewsInterface newsInterface) {
            super(itemView);

            image = itemView.findViewById(R.id.img_article);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtAuthor = itemView.findViewById(R.id.txt_link);

//            chkSaved = itemView.findViewById(R.id.chkSaved);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(newsInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            newsInterface.onArticleClick(pos);
                        }
                    }
                }
            });

//            chkSaved.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    boolean isChecked = ((CheckBox) view).isChecked();
//
//                    if(newsInterface != null){
//                        int pos = getAdapterPosition();
//                        if (pos != RecyclerView.NO_POSITION){
//                            if (isChecked){
//                                newsInterface.onChkSaveClick(pos);
//                            }
//                            else {
//                                newsInterface.onChkNoSaveClick(pos);
//                            }
//                        }
//                    }
//                }
//            });
        }
    }

}
