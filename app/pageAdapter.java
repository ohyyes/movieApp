public class pageAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<cafeList> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public pageAdapter(ArrayList<cafeList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafelist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_cafeName.setText(arrayList.get(position).getCafeName());
        holder.tv_address.setText(arrayList.get(position).getAddress());
        holder.tv_phone.setText(arrayList.get(position).getPhone());
        holder.tv_likeNum.setText(String.valueOf(arrayList.get(position).getLikeNum()));
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_cafeName;
        TextView tv_address;
        TextView tv_phone;
        TextView tv_likeNum;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_cafeName = itemView.findViewById(R.id.tv_cafeName);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.tv_phone = itemView.findViewById(R.id.tv_phone);
            this.tv_likeNum = itemView.findViewById(R.id.tv_likeNum);
        }
    }
}