package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.aforce.beans.Product;

public class productDAO extends DAO{
	/*商品の新規登録*/
	public boolean addProduct(Product product) throws Exception {
	    boolean result;
	    Connection con = getConnection();

	    PreparedStatement st1 = con.prepareStatement(
            "INSERT INTO product (category_id, name, price, quantity, img_name) VALUES (?, ?, ?, ?, ?)");
        st1.setInt(1, product.getCategory_id());
        st1.setString(2, product.getName());
        st1.setInt(3,product.getPrice() );
        st1.setInt(4, product.getQuantity());
        st1.setString(5, product.getImg());

        st1.executeUpdate();
        st1.close();

        result = true;
    

	    con.close();
	    return result;
	}
	
	/*商品の検索*/
	
	public List<Product> search(String keyword) throws Exception {
		List<Product> list=new ArrayList<>();

		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select name,price,img_name from product where name like ?");
		st.setString(1, "%"+keyword+"%");
		ResultSet rs=st.executeQuery();

		while (rs.next()) {
			Product p=new Product();
			p.setName(rs.getString("name"));
			p.setPrice(rs.getInt("price"));
			p.setImg(rs.getString("img_name"));
			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}
	/*商品の一覧*/
	public List<Product> findAll()throws Exception {
		List<Product> list=new ArrayList<>();
		
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select product_id,name,price,img_name from product"
		);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Product p=new Product();
			p.setProduct_id(rs.getInt("product_id"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getInt("price"));
			p.setImg(rs.getString("img_name"));
			list.add(p);
		}
		st.close();
		con.close();
		
		return list;
		
		
	}
	
	/*商品の一部検索（修正画面に必須）*/
	public Product findProduct(int id) throws Exception {
	    Product p = null;

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(
	             "SELECT product_id, name, price FROM product WHERE product_id = ?")) {

	        st.setInt(1, id);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	            p = new Product();                // ★ここを忘れずに
	            p.setProduct_id(rs.getInt("product_id"));
	            p.setName(rs.getString("name"));
	            p.setPrice(rs.getInt("price"));
	        }
	    }
	    return p;      // 見つからなければ null
	}
	/*商品の修正*/
	
	public boolean updateProduct(Product p) throws Exception {

	    String sql = "UPDATE product SET name = ?, price = ? WHERE product_id = ?";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql)) {

	        st.setString(1, p.getName());
	        st.setInt(2, p.getPrice());
	        st.setInt(3, p.getProduct_id());

	        return st.executeUpdate() == 1;   // true = 成功
	    }
	}
	
	/*商品の削除*/
	public boolean productDelete(int id) throws Exception {
	    Connection con = getConnection();

	    PreparedStatement st = con.prepareStatement(
	        "delete from product where product_id = ?"
	    );
	    st.setInt(1, id);

	    int rowsDeleted = st.executeUpdate();

	    st.close();

	    return rowsDeleted > 0;
	}


}
