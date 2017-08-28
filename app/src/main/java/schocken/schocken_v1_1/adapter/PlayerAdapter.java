package schocken.schocken_v1_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import schocken.schocken_v1_1.R;
import schocken.schocken_v1_1.player.Player;

/**
 * Created by marco on 28.08.17.
 */

public class PlayerAdapter extends ArrayAdapter<Player>{

    private class ViewHolder {

    }



    public PlayerAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void addAll(Player... items) {
        super.addAll(items);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Player player = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.player_row,parent,false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(player.getName());
        return rowView;

    }
}
