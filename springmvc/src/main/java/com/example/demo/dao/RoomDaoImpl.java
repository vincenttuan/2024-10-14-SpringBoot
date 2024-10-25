package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.bean.Room;

@Repository
@PropertySource("classpath:sql.properties")
public class RoomDaoImpl implements RoomDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${room.sql.findAllRooms}")
	private String findAllRoomsSql;
	
	@Override
	public List<Room> findAllRooms() {
		return jdbcTemplate.query(findAllRoomsSql, new BeanPropertyRowMapper<>(Room.class));
	}

	@Override
	public Optional<Room> getRoomById(Integer roomId) {
		String sql = "select roomId, roomName, roomSize from room where roomId=?";
		Room room = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class), roomId);
		return room == null ? Optional.empty() : Optional.of(room);
	}

	@Override
	public void addRoom(Room room) {
		String sql = "insert into (roomId, roomName, roomSize) values(?, ?, ?)";
		jdbcTemplate.update(sql, room.getRoomId(), room.getRoomName(), room.getRoomId());
	}

	@Override
	public void updateRoom(Integer roomId, Room room) {
		String sql = "update room set roomName=?, roomSize=? where roomId=?";
		jdbcTemplate.update(sql, room.getRoomName(), room.getRoomSize(), room.getRoomId());
	}

	@Override
	public void deleteRoom(Integer roomId) {
		String sql = "delete from room where roomId=?";
		jdbcTemplate.update(sql, roomId);
	}
	
}
