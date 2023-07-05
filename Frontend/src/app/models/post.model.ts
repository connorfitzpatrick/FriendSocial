export interface Post {
    id: number;
    profileId: number;
    postType: string;
    content: string;
    timestamp: Date;
    imageUrl: string;
  }