package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.aforce.beans.Cart;
import jp.co.aforce.beans.Product;


public class CartDAO extends DAO {

    // オープンカートを1件取得（status=0）
    public Cart getOpenCart(String memberId) throws Exception {
        String sql = "SELECT * FROM cart WHERE member_id = ? AND status = 0 LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cart cart = new Cart();
                    cart.setCart_id(rs.getInt("cart_id"));
                    cart.setMember_id(rs.getString("member_id"));
                    cart.setStatus(rs.getInt("status"));
                    cart.setCreated_at(rs.getTimestamp("created_at"));
                    cart.setUpdated_at(rs.getTimestamp("updated_at"));
                    return cart;
                }
            }
        }
        return null;
    }

    // オープンカート内の商品の詳細情報 + 数量を取得（Product に数量を一時的に追加して返す）
    public List<Product> getCartProductsWithAmount(int cartId) throws Exception {
        String sql = """
            SELECT p.*, ci.amount
            FROM cart_item ci
            JOIN product p ON ci.product_id = p.product_id
            WHERE ci.cart_id = ?
        """;

        List<Product> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProduct_id(rs.getInt("product_id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getInt("price"));
                    p.setImg(rs.getString("img_name"));
                    p.setAmount(rs.getInt("amount"));
                    list.add(p);
                }
            }
        }

        return list;
    }
    
    //カート内の削除処理
    
    public int deleteCartItem(int cartId, int productId) throws Exception {
        String sql = "DELETE FROM cart_item WHERE cart_id = ? AND product_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            return ps.executeUpdate();
        }
    }
    
    
    
}
