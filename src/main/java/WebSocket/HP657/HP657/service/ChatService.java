package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.MessageDto;
import WebSocket.HP657.HP657.entity.ChatRoomEntity;
import WebSocket.HP657.HP657.entity.MessageEntity;
import WebSocket.HP657.HP657.entity.UserEntity;
import WebSocket.HP657.HP657.repository.ChatRoomRepository;
import WebSocket.HP657.HP657.repository.MessageRepository;
import WebSocket.HP657.HP657.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ChatService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    public ChatRoomEntity createChatRoom(String name) {
        // Lombok 빌더를 사용하여 채팅방 생성
        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .chatroomname(name)
                .members(new HashSet<>()) // 초기 멤버 목록을 비워두기
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    // 채팅방에 사용자 초대
    public ChatRoomEntity inviteUserToRoom(Long roomId, Long userId) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        room.getMembers().add(user);
        return chatRoomRepository.save(room);  // 업데이트된 채팅방 엔티티 반환
    }

    public MessageDto processMessage(MessageDto messageDto, String roomId) {
        // roomId를 사용하여 채팅방을 찾고, 채팅방이 유효한지 확인
        ChatRoomEntity room = chatRoomRepository.findById(Long.parseLong(roomId))
                .orElseThrow(() -> new RuntimeException("Chat room not found"));

        // 메시지 송신자가 채팅방의 멤버인지 확인
        UserEntity sender = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!room.getMembers().contains(sender)) {
            throw new RuntimeException("User is not a member of this room");
        }

        // 메시지 엔티티 생성 및 저장
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUserId(sender.getUserId());
        messageEntity.setContent(messageDto.getMessage());
        messageEntity.setUserId(room.getChatRoomId());
        messageRepository.save(messageEntity);

        // 처리된 메시지 정보 반환
        return new MessageDto(sender.getUserId(), messageEntity.getContent());
    }
}
