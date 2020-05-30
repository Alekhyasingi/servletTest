package com.item.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.item.model.Item;
import java.io.*;

@WebServlet(name = "ItemServlet", urlPatterns = "/itemServlet")
public class ItemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int count = Integer.valueOf(request.getParameter("limit"));
		List<Item> items = new ArrayList<>();
		// added file to read the items from it
		String filename = "WEB-INF/item.txt";
		ServletContext context = getServletContext();
		InputStream is = context.getResourceAsStream(filename);
		if (is != null) {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);

			String text;

			int i = 0;
			while ((text = reader.readLine()) != null && i < count) {
				String[] parts = text.split(" ");
				Item item = new Item(Integer.valueOf(parts[0]), parts[1], parts[2]);
				items.add(item);				
				i++;
			}
		}
		// file changes end

		String itemJsonString = this.gson.toJson(items);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(itemJsonString);
		out.flush();
	}
}
