package com.kkumteul.domain.book.repository;

import com.kkumteul.domain.book.entity.Book;
import com.kkumteul.domain.book.entity.BookLike;
import com.kkumteul.domain.childprofile.entity.ChildProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLikeRepository extends JpaRepository<BookLike, Long> {

    @Query("SELECT b From BookLike b JOIN FETCH b.book WHERE b.childProfile.id = :childProfileId")
    List<BookLike> findBookLikesWithBookByChildProfileId(@Param("childProfileId") Long childProfileId);

    @Query(value = """
        SELECT bl
          FROM BookLike bl
         WHERE bl.childProfile.id = :childProfileId AND bl.book.id = :bookId
       """)
    Optional<BookLike> findByChildProfileAndBook(
            @Param("childProfileId") Long childProfileId,
            @Param("bookId") Long bookId
    );
}
